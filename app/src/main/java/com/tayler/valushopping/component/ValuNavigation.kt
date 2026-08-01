package com.tayler.valushopping.component

import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tayler.valushopping.R
import com.tayler.valushopping.ui.home.ScreenHome
import com.tayler.valushopping.ui.home.category.ScreenCategory
import com.tayler.valushopping.ui.home.config.ScreenConfig
import com.tayler.valushopping.ui.home.init.ScreenInit
import com.tayler.valushopping.ui.home.product.ScreenProduct
import com.tayler.valushopping.ui.splash.ScreenSplash
import kotlinx.serialization.Serializable

@Composable
fun ValeNavigationMain() {

    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenVale.SplashScreen,
        exitTransition = { ExitTransition.None }
    ) {
        composable<ScreenVale.SplashScreen> {
            ScreenSplash()
        }

        composable<ScreenVale.HomeScreen> {
            ScreenHome()
        }

        composable<ScreenVale.LoginScreen> {
            ScreenHome()
        }

    }
}


@Serializable
sealed class ScreenVale(
) {

    @Serializable
    data object SplashScreen: ScreenVale()
    @Serializable
    data object HomeScreen: ScreenVale()

    @Serializable
    data object LoginScreen: ScreenVale()
}

@Serializable
sealed interface TayRoute {
    @Serializable object Init : TayRoute
    @Serializable object Product : TayRoute
    @Serializable object Category : TayRoute
    @Serializable object Config : TayRoute
}

sealed class TayDestinations(
    val route: TayRoute,
    val title: String,
    val icon: Int,
    val iconSelected: Int = 0
) {
    data object InitNavScreen: TayDestinations(TayRoute.Init,"Inicio", R.drawable.ic_home)
    data object ProductNavScreen: TayDestinations(TayRoute.Product,"Productos",R.drawable.ic_clothes)
    data object CategoryScreenNavScreen: TayDestinations(TayRoute.Category,"Categorias",R.drawable.ic_category)
    data object ConfigNavScreen: TayDestinations(TayRoute.Config,"Config",R.drawable.ic_admin)
}

@Composable
fun NavigationNavBarHost() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = TayRoute.Init,
        exitTransition = {
            ExitTransition.None
        }) {
        composable<TayRoute.Init> { ScreenInit() }
        composable<TayRoute.Product> { ScreenProduct() }
        composable<TayRoute.Category> { ScreenCategory() }
        composable<TayRoute.Config>  { ScreenConfig() }
    }
}