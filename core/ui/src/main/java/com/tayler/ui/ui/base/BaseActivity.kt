package com.tayler.ui.ui.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.ui.AppDataVale
import com.tayler.ui.LocalAppDataVale
import com.tayler.ui.ValeTheme
import com.tayler.ui.mapperError
import com.valu.uitaycompose.loading.UiProgress
import com.valu.uitaycompose.modal.UiTayDialog
import com.valu.uitaycompose.model.UiTayDialogModel
import jakarta.inject.Inject

abstract class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var globalUiStateManager: GlobalUiStateManager

    @Inject
    lateinit var appDataVale: AppDataVale

    @Composable
    fun RenderGenericDialog(
        image: Int,
        title: String,
        subTitle: String,
        onResult: (Boolean) -> Unit
    ) {
        UiTayDialog(
            model = UiTayDialogModel(
                image = image,
                title = title,
                subTitle = subTitle,
                isCancel = false
            )
        ) { result ->
            onResult(result)
        }
    }

    @Composable
    abstract fun SetScreenConfig()
    abstract fun setDataGlobal()
    open fun allowRecentsScreenshot(): Boolean = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setDataGlobal()
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setRecentsScreenshotEnabled(allowRecentsScreenshot())
        }
        setContent {
            ValeTheme {
                CompositionLocalProvider(
                    LocalAppDataVale provides appDataVale,
                    LocalGlobalUiStateManager provides globalUiStateManager
                ) {
                    val uiState by globalUiStateManager.uiState.collectAsStateWithLifecycle()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .windowInsetsTopHeight(WindowInsets.statusBars)
                                    .background(uiState.statusBarColor)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .windowInsetsPadding(WindowInsets.navigationBars)
                            ) {
                                SetScreenConfig()
                            }
                        }

                        if (uiState.loading) {
                            UiProgress()
                        }

                        if (uiState.error) {
                            val errorInfo = uiState.errorType.mapperError(this@BaseActivity, appDataVale)
                            RenderGenericDialog(
                                image = errorInfo.first,
                                title = errorInfo.second,
                                subTitle = errorInfo.third
                            ) { dialogResult ->
                                globalUiStateManager.updateUiState { current ->
                                    current.copy(
                                        error = false,
                                        popUpGeneric = true,
                                        popUpGenericValue = dialogResult
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
