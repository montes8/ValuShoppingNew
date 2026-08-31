package com.tayler.home

import androidx.compose.runtime.Composable
import com.tayler.navigation.ScreenInitNav

@Composable
fun HomeFlow(onNavigateToMain: (ScreenInitNav) -> Unit) {
    ScreenHome(onNavigateToMain = onNavigateToMain)
}
