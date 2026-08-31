package com.tayler.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenInitNav {
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
    
    // Rutas para los nuevos flujos
    @Serializable
    data object AuthFlow : ScreenInitNav()
    @Serializable
    data object ConfigProductFlow : ScreenInitNav()
}

@Serializable
sealed interface AuthRoutes {
    @Serializable
    data object Login : AuthRoutes
    @Serializable
    data object Register : AuthRoutes
}

@Serializable
sealed interface ConfigProductRoutes {

    @Serializable
    data object OptionConfig : ConfigProductRoutes
    @Serializable
    data object ListProduct : ConfigProductRoutes
    @Serializable
    data object AddProduct : ConfigProductRoutes
    @Serializable
    data class ImageProduct(val id: String) : ConfigProductRoutes
    @Serializable
    data object Param : ConfigProductRoutes
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

fun Int.mapperNavBar() : TayRoute {
    return when (this) {
        0 -> TayRoute.Init
        1 -> TayRoute.Product
        2 -> TayRoute.Category
        3 -> TayRoute.Config
        else -> TayRoute.Init
    }
}
