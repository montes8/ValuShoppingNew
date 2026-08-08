package com.tayler.valushopping.application

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.tayler.usecases.AppUseCase
import com.valu.uitaycompose.utils.extension.changeIcon
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ApplicationVale : Application(), DefaultLifecycleObserver {

    @Inject
    lateinit var appUseCase: AppUseCase

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super<Application>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        applicationScope.launch {
            val data = appUseCase.configInitParam()
            val iconActual = if (data.idIconOld == "0" || data.idIconOld.isEmpty()) "Principal" else data.idIconOld
            val iconNew = data.idIcon.ifEmpty { "Principal" }

            if (iconActual != iconNew) {
                val updatedData = data.copy(idIconOld = iconNew)
                appUseCase.saveParam(updatedData)
                changeIcon(activeAliasName = iconNew, oldAliasName = iconActual)
            }
        }
    }
}
