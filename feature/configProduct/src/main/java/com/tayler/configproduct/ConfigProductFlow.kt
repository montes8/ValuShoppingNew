package com.tayler.configproduct

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tayler.configproduct.ui.ScreenAddProduct
import com.tayler.configproduct.ui.ScreenImageProduct
import com.tayler.configproduct.ui.ScreenListProduct
import com.tayler.configproduct.ui.ScreenOption
import com.tayler.configproduct.ui.ScreenParam
import com.tayler.navigation.ConfigProductRoutes
import com.tayler.ui.ValuScreen

@Composable
fun ConfigProductFlow(onFinish: () -> Unit) {
    val internalNavController = rememberNavController()
    
    NavHost(navController = internalNavController, startDestination = ConfigProductRoutes.OptionConfig) {

        composable<ConfigProductRoutes.OptionConfig> {
            ValuScreen(title = "Lista de Productos", onBackClick = onFinish) {
                ValuScreen(title = "elije un opcion ", onBackClick = onFinish) {
                    ScreenOption()
                }
            }
        }
        composable<ConfigProductRoutes.ListProduct> {
            ValuScreen(title = "Lista de Productos", onBackClick = onFinish) {
                ScreenListProduct()
            }
        }
        composable<ConfigProductRoutes.AddProduct> {
            ValuScreen(title = "Agregar Producto", onBackClick = { internalNavController.popBackStack() }) {
                ScreenAddProduct()
            }
        }
        composable<ConfigProductRoutes.ImageProduct> {
            ValuScreen(title = "Detalle Interno", onBackClick = { internalNavController.popBackStack() }) {
                ScreenImageProduct()
            }
        }
        composable<ConfigProductRoutes.Param> {
            ValuScreen(title = "Parámetros", onBackClick = { internalNavController.popBackStack() }) {
                ScreenParam()
            }
        }
    }
}
