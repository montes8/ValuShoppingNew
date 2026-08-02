package com.tayler.valushopping.ui.base

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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.valushopping.component.DialogGeneric
import com.tayler.valushopping.component.ProgressDialogApp
import com.tayler.valushopping.utils.ValeTheme
import com.tayler.valushopping.utils.mapperError

abstract class BaseActivity : ComponentActivity() {

    @Composable
    abstract fun SetScreenConfig()
    abstract fun setDataGlobal()
    open fun getViewModel(): BaseViewModel? = null
    open fun allowRecentsScreenshot(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataGlobal()
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setRecentsScreenshotEnabled(allowRecentsScreenshot())
        }
        setContent {
            ValeTheme {
                val viewModel = getViewModel()
                val uiState by viewModel?.uiStateBase?.collectAsStateWithLifecycle()
                    ?: remember { mutableStateOf(BaseUiState()) }
               Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsTopHeight(WindowInsets.statusBars)
                                .background(uiState.statusBarColor)
                        )

                        Box(modifier = Modifier.fillMaxSize()) {
                            SetScreenConfig()
                        }
                    }

                    if (uiState.loading) {
                        ProgressDialogApp()
                    }

                    if (uiState.error) {
                        DialogGeneric(message = uiState.errorType.mapperError().third) { dialogResult ->
                            viewModel?.dismissErrorDialog(dialogResult)
                        }
                    }
                }
            }
        }
    }
}
