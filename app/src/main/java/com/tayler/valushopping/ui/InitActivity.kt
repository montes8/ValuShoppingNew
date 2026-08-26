package com.tayler.valushopping.ui

import androidx.compose.runtime.Composable
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.tayler.repository.utils.SecurityUtils
import com.tayler.valushopping.R
import com.tayler.valushopping.component.ValeNavigationInit
import com.tayler.valushopping.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class InitActivity : BaseActivity() {

    companion object {
        private const val UPDATE_CODE = 100
    }
    private val updateOptions = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()

    override fun setDataGlobal() {
        window.decorView.post {
            validateVersionUpdate()
        }
    }

    @Composable
    override fun SetScreenConfig() {
        val warning = SecurityUtils.securityWarning

        if (warning != SecurityUtils.SecurityWarningType.NONE) {
            val (titleRes, subTitleRes) = when (warning) {
                SecurityUtils.SecurityWarningType.ROOT -> 
                    R.string.text_error_root to R.string.text_error_message_root
                SecurityUtils.SecurityWarningType.DEVELOPER_MODE -> 
                    R.string.text_error_developer to R.string.text_error_message_developer
                SecurityUtils.SecurityWarningType.EMULATOR -> 
                    R.string.text_error_emulator to R.string.text_error_message_emulator
                else -> R.string.text_error_root to R.string.text_error_message_root
            }

            RenderGenericDialog(
                image = R.drawable.ic_info_error,
                title = getString(titleRes),
                subTitle = getString(subTitleRes)
            ) {
                exitProcess(0)
            }
        } else {
            ValeNavigationInit()
        }
    }

    private fun validateVersionUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        this,
                        updateOptions,
                        UPDATE_CODE
                    )
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        appUpdateInfoTask.addOnFailureListener {
            //not code
        }
    }
}

