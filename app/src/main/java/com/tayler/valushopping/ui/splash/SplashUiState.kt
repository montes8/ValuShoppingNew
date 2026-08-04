package com.tayler.valushopping.ui.splash

import androidx.compose.ui.graphics.Color
import com.valu.uitaycompose.utils.UI_EMPTY

data class SplashUiState(
    val welcomeText: String = UI_EMPTY,
    val textColor: Color = Color.Black,
    val showLogo: Boolean = false
)