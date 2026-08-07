package com.tayler.valushopping.ui

import androidx.compose.runtime.Composable
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.component.ValeNavigationInit
import com.tayler.valushopping.ui.base.BaseActivity
import com.valu.uitaycompose.utils.extension.changeIcon
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class InitActivity : BaseActivity() {

    @Inject
    lateinit var appUseCase: AppUseCase

    companion object {
        private const val UPDATE_CODE = 100
    }
    private val updateOptions = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()

    override fun setDataGlobal() {
        checkAndUpdateAppIcon()
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

    private fun checkAndUpdateAppIcon() {
        val iconActual = appUseCase.geIdIconOld().ifEmpty { "Principal" }
        val iconNew = appUseCase.geIdIcon().ifEmpty { "Principal" }

        if (iconActual != iconNew) {
            changeIcon(activeAliasName = iconNew, oldAliasName = iconActual) { success ->
                if (success) {
                    appUseCase.saveIdIconOld(iconNew)
                }
            }
        }
    }
}

