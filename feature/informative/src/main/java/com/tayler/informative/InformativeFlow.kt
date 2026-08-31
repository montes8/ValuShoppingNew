package com.tayler.informative

import androidx.compose.runtime.Composable

@Composable
fun InformativeFlow(onBackClick: () -> Unit) {
    ScreenAbout(onNavigateBack = onBackClick)
}
