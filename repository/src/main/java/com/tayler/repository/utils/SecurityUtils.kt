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

    var isRootDetected: Boolean = false
        private set

    fun verifyIntegrity(context: Context, isDebug: Boolean, versionName: String, model: String) {
        val expectedHash = if (isDebug) BuildConfig.HASH_ONE else BuildConfig.HASH_TWO
        
        val isTampered = isAppTampered(context, expectedHash)
        isRootDetected = isDeviceRooted()
        if (isTampered || isRootDetected) {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US).apply {
                timeZone = java.util.TimeZone.getTimeZone("UTC")
            }

            val motivo = when {
                isTampered && isRootDetected -> "APK_MODIFICADO_Y_ROOT"
                isTampered -> "APK_MODIFICADO"
                else -> "DISPOSITIVO_ROOT"
            }

            val alert = SecurityAlertRequest(
                event = "APK_MODIFICADO_DETECTADO",
                packageApp = context.packageName,
                version = versionName,
                timestamp = sdf.format(java.util.Date()),
                model = model,
                reason = motivo
            )
            sendSecurityAlertBlocking(BuildConfig.BASE_URL, alert)

            if (isTampered) {
                android.os.Process.killProcess(android.os.Process.myPid())
                kotlin.system.exitProcess(1)
            }
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
                conn.outputStream.use { os ->
                    os.write(jsonBody.toByteArray(Charsets.UTF_8))
                }

                conn.responseCode
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
