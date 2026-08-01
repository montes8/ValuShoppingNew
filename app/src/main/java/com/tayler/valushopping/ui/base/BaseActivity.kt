package com.tayler.valushopping.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataGlobal()
        setContent {
            ValeTheme {
                val viewModel = getViewModel()

                val uiState by viewModel?.uiStateBase?.collectAsStateWithLifecycle()
                    ?: remember { mutableStateOf(BaseUiState()) }

                if (uiState.loading) {
                    ProgressDialogApp()
                }

                if (uiState.error) {
                    DialogGeneric(message = uiState.errorType.mapperError()) { dialogResult ->
                        viewModel?.dismissErrorDialog(dialogResult)
                    }
                }

                SetScreenConfig()
            }
        }
    }
}