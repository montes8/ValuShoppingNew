package com.tayler.valushopping.ui.splash

import androidx.compose.ui.graphics.Color
import com.tayler.valushopping.utils.DEFAULT_TEXT_WELCOME

data class SplashUiState(
    val welcomeText: String = DEFAULT_TEXT_WELCOME,
    val textColor: Color = Color.Black,
    val showLogo: Boolean = false
)