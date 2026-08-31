package com.tayler.profile

import androidx.compose.runtime.Composable

@Composable
fun ProfileFlow(onBackClick: () -> Unit) {
    ScreenProfile(onNavigateBack = onBackClick)
}
