package com.tayler.valushopping.component

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tayler.entity.ProductModel
import com.tayler.valushopping.ui.about.ScreenAbout
import com.tayler.valushopping.ui.detail.ScreenDetail
import com.tayler.valushopping.ui.home.ScreenHome
import com.tayler.valushopping.ui.home.category.ScreenCategory
import com.tayler.valushopping.ui.home.config.ScreenConfig
import com.tayler.valushopping.ui.home.init.ScreenInit
import com.tayler.valushopping.ui.home.product.ScreenProduct
import com.tayler.valushopping.ui.profile.ScreenProfile
import com.tayler.valushopping.ui.splash.ScreenSplash
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Composable
fun ValeNavigationInit() {

    val navController: NavHostController = rememberNavController()
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
            ScreenHome{ screenInitNav->
                navController.navigate(screenInitNav)
            }
        }

        composable<ScreenInitNav.DetailScreen>{ backStackEntry ->
            val detailRoute = backStackEntry.toRoute<ScreenInitNav.DetailScreen>()
            ScreenDetail(product = Json.decodeFromString<ProductModel>(detailRoute.productJson)) {
                navController.popBackStack()
            }
        }

        composable<ScreenInitNav.ProfileScreen> {
            ScreenProfile{
                navController.popBackStack()
            }
        }

        composable<ScreenInitNav.AboutScreen> {
            ScreenAbout{
                navController.popBackStack()
            }
        }

    }
}

@Serializable
sealed class ScreenInitNav{

    @Serializable
    data object SplashScreen : ScreenInitNav()

    @Serializable
    data object HomeScreen : ScreenInitNav()
    @Serializable
    data class DetailScreen(val productJson: String) : ScreenInitNav()

    @Serializable
    data object ProfileScreen : ScreenInitNav()

    @Serializable
    data object AboutScreen : ScreenInitNav()
}

@Serializable
sealed interface TayRoute {
    @Serializable
    object Init : TayRoute
    @Serializable
    object Product : TayRoute
    @Serializable
    object Category : TayRoute
    @Serializable
    object Config : TayRoute
}


@Composable
fun NavigationNavBarHost(navController: NavHostController,onNavigateToMain: (ScreenInitNav) -> Unit) {

    NavHost(
        navController = navController, startDestination = TayRoute.Init,
        exitTransition = {
            ExitTransition.None
        }) {
        composable<TayRoute.Init> { ScreenInit() }
        composable<TayRoute.Product> { ScreenProduct{data->
            onNavigateToMain.invoke(ScreenInitNav.DetailScreen(productJson = Json.encodeToString(data)))
            }
        }
        composable<TayRoute.Category> { ScreenCategory() }
        composable<TayRoute.Config> { ScreenConfig{
            onNavigateToMain.invoke(ScreenInitNav.AboutScreen)

        }
        }

    }
}


fun Int.mapperNavBar() : TayRoute {
    return when (this) {
        0 -> TayRoute.Init
        1 -> TayRoute.Product
        2 -> TayRoute.Category
        3 -> TayRoute.Config
        else -> TayRoute.Init
    }
}