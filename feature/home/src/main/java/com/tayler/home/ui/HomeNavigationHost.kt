package com.tayler.home.ui

import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tayler.navigation.ScreenInitNav
import com.tayler.navigation.TayRoute
import com.tayler.home.ui.category.ScreenCategory
import com.tayler.home.ui.config.ScreenConfig
import com.tayler.home.ui.init.ScreenInit
import com.tayler.home.ui.product.ScreenProduct
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun HomeNavigationHost(navController: NavHostController, onNavigateToMain: (ScreenInitNav) -> Unit) {

    NavHost(
        navController = navController, startDestination = TayRoute.Init,
        exitTransition = {
            ExitTransition.None
        }) {
        composable<TayRoute.Init> { ScreenInit() }
        composable<TayRoute.Product> { ScreenProduct { data ->
            onNavigateToMain.invoke(ScreenInitNav.DetailScreen(productJson = Json.encodeToString(data)))
        }
        }
        composable<TayRoute.Category> { ScreenCategory() }
        composable<TayRoute.Config> { ScreenConfig {
            onNavigateToMain.invoke(ScreenInitNav.AboutScreen)
        }
        }
    }
}
