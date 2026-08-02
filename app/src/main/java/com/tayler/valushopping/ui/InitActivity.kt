package com.tayler.valushopping.ui

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.tayler.valushopping.component.ValeNavigationMain
import com.tayler.valushopping.ui.base.BaseActivity
import com.tayler.valushopping.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun getViewModel(): BaseViewModel {
        return appViewModel
    }

    companion object {
        private const val UPDATE_CODE = 100
    }
    private val updateOptions = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()

    override fun setDataGlobal() {
        validateVersionUpdate()
        appViewModel.loadParam()
        appViewModel.loadSplashData()
    }

    @Composable
    override fun SetScreenConfig() {
            ValeNavigationMain()
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

