package com.tayler.valushopping.ui.base

import androidx.compose.ui.graphics.Color
import com.valu.uitaycompose.utils.tay_pink_100

data class BaseUiState(
    var popUpGenericValue:Boolean = false,
    var popUpGeneric: Boolean = false,
    var loading: Boolean = false,
    var shimmer: Boolean = false,
    var error: Boolean = false,
    var statusBarColor: Color = tay_pink_100,
    var errorType: Throwable = Throwable()
)