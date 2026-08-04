package com.tayler.valushopping.ui

import androidx.compose.runtime.Composable
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.tayler.valushopping.component.ValeNavigationInit
import com.tayler.valushopping.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

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
        ValeNavigationInit()
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

