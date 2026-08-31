package com.tayler.valushopping.component

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tayler.entity.ProductModel
import com.tayler.navigation.ScreenInitNav
import com.tayler.informative.InformativeFlow
import com.tayler.home.HomeFlow
import com.tayler.detail.DetailFlow
import com.tayler.profile.ProfileFlow
import com.tayler.auth.authGraph
import com.tayler.configproduct.ConfigProductFlow
import com.tayler.valushopping.ui.splash.AppViewModel
import com.tayler.valushopping.ui.splash.ScreenSplash
import kotlinx.serialization.json.Json

@Composable
fun ValeNavigationInit() {

    val navController: NavHostController = rememberNavController()
    val onBack: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = ScreenInitNav.SplashScreen,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<ScreenInitNav.SplashScreen> {
            ScreenSplash {
                navController.navigate(ScreenInitNav.HomeScreen) {
                    popUpTo(ScreenInitNav.SplashScreen) { inclusive = true }
                }
            }
        }

        composable<ScreenInitNav.HomeScreen> {
            HomeFlow { screenInitNav ->
                navController.navigate(screenInitNav)
            }
        }

        composable<ScreenInitNav.DetailScreen>{ backStackEntry ->
            val detailRoute = backStackEntry.toRoute<ScreenInitNav.DetailScreen>()
            val aViewModel: AppViewModel = hiltViewModel()
            DetailFlow(
                product = Json.decodeFromString<ProductModel>(detailRoute.productJson),
                onSaveHistory = { type -> aViewModel.saveHistory(type) },
                onBackClick = onBack
            )
        }

        composable<ScreenInitNav.ProfileScreen> {
            ProfileFlow(onBackClick = onBack)
        }

        composable<ScreenInitNav.AboutScreen> {
            InformativeFlow(onBackClick = onBack)
        }

        // --- OPCIÓN B: ESTILO DELEGADO (Grafo compartido) ---
        // El flujo de Auth registra sus pantallas directamente en el NavHost global
        authGraph(navController)

        // --- OPCIÓN A: ESTILO INDEPENDIENTE (Navegación interna) ---
        // El flujo de ConfigProduct maneja su propio NavController interno
        composable<ScreenInitNav.ConfigProductFlow> {
            ConfigProductFlow(onFinish = onBack)
        }
    }
}


