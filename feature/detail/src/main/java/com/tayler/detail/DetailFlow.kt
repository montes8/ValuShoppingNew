package com.tayler.detail

import androidx.compose.runtime.Composable
import com.tayler.entity.ProductModel

@Composable
fun DetailFlow(product: ProductModel, onSaveHistory: (String) -> Unit, onBackClick: () -> Unit) {
    ScreenDetail(product = product, onSaveHistory = onSaveHistory, onBackClick = onBackClick)
}
