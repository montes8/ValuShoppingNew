package com.tayler.valushopping.ui.base

import androidx.compose.ui.graphics.Color

data class BaseUiState(
    var popUpGenericValue:Boolean = false,
    var popUpGeneric: Boolean = false,
    var loading: Boolean = false,
    var error: Boolean = false,
    var statusBarColor: Color = Color.Cyan,
    var errorType: Throwable = Throwable()
)