@file:Suppress("DEPRECATION")

package com.tayler.repository.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.tayler.entity.SecurityAlertRequest
import com.tayler.repository.BuildConfig
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

object SecurityUtils {

    enum class SecurityWarningType {
        NONE, ROOT, DEVELOPER_MODE, EMULATOR
    }

    var securityWarning: SecurityWarningType = SecurityWarningType.NONE
        private set
    var isDeveloperModeDetected: Boolean = false
        private set

    fun verifyIntegrity(context: Context, isDebug: Boolean,
                        versionName: String, model: String,
                        uuid: String,identifier: String) {
        val expectedHash = if (isDebug) BuildConfig.HASH_ONE else BuildConfig.HASH_TWO
        
        val isTampered = isAppTampered(context, expectedHash)
        val isRoot = isDeviceRooted()
        isDeveloperModeDetected = if (!isDebug) isDeveloperModeEnabled(context) else false
        val isEmu = isEmulator()

        val installerName = context.packageManager.getInstallerPackageName(context.packageName) ?: "MANUAL_INSTALL"
        val isUnofficial = installerName != "com.android.vending"

        securityWarning = when {
            isRoot -> SecurityWarningType.ROOT
            isEmu -> SecurityWarningType.EMULATOR
            isDeveloperModeDetected -> SecurityWarningType.DEVELOPER_MODE
            else -> SecurityWarningType.NONE
        }

        if (isTampered || securityWarning != SecurityWarningType.NONE || isUnofficial) {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US).apply {
                timeZone = java.util.TimeZone.getTimeZone("UTC")
            }

            val motivo = when {
                isTampered && isRoot -> "APK_MODIFICADO_Y_ROOT"
                isTampered -> "APK_MODIFICADO"
                isRoot -> "DEVICE_ROOT"
                isEmu -> "DEVICE_EMULADOR"
                isDeveloperModeDetected -> "MODO_DESARROLLADOR_ACTIVO"
                isUnofficial -> "INSTALACION_NO_OFICIAL"
                else -> "SECURITY_DETECTADA"
            }

            val alert = SecurityAlertRequest(
                event = "SECURITY_ALERT",
                packageApp = context.packageName,
                version = versionName,
                timestamp = sdf.format(java.util.Date()),
                model = model,
                reason = motivo,
                uuid = uuid,
                identifier = identifier,
                installer = installerName
            )
            sendSecurityAlertBlocking(BuildConfig.BASE_URL, alert)

            if (isTampered) {
                android.os.Process.killProcess(android.os.Process.myPid())
                kotlin.system.exitProcess(1)
            }
        }
    }

    fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
    }

    fun isDeveloperModeEnabled(context: Context): Boolean {
        return try {
            val devMode = android.provider.Settings.Global.getInt(
                context.contentResolver,
                android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
            ) != 0
            
            val adbEnabled = android.provider.Settings.Global.getInt(
                context.contentResolver,
                android.provider.Settings.Global.ADB_ENABLED, 0
            ) != 0
            
            devMode || adbEnabled
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isDeviceRooted(): Boolean {
        val buildTags = Build.TAGS
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true
        }

        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in paths) {
            if (java.io.File(path).exists()) return true
        }

        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val reader = java.io.BufferedReader(java.io.InputStreamReader(process.inputStream))
            reader.readLine() != null
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        } finally {
            process?.destroy()
        }
    }

    fun isAppTampered(context: Context, expectedHash: String): Boolean {
        try {
            val packageName = context.packageName
            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val pkgInfo = context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
                pkgInfo.signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                val pkgInfo = context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )
                pkgInfo.signatures
            }

            if (!signatures.isNullOrEmpty()) {
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(signatures[0].toByteArray())
                val currentHash = digest.joinToString(":") { "%02X".format(it) }
                return currentHash != expectedHash
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    fun sendSecurityAlertBlocking(baseUrl: String, request: SecurityAlertRequest) {
        val thread = Thread {
            try {
                val fullUrl = if (baseUrl.endsWith("/")) "${baseUrl}security" else "$baseUrl/security"
                val url = URL(fullUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                conn.doOutput = true
                conn.connectTimeout = 3000
                conn.readTimeout = 3000

                val jsonBody = Json.encodeToString(request)
                jsonBody.uiTayLog("SECURITY_SERVICE_REQUEST")

                conn.outputStream.use { os ->
                    os.write(jsonBody.toByteArray(Charsets.UTF_8))
                }

                val responseCode = conn.responseCode
                "Response Code: $responseCode".uiTayLog("SECURITY_SERVICE_RESPONSE")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
