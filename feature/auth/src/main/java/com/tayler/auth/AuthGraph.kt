package com.tayler.auth

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tayler.navigation.AuthRoutes
import com.tayler.navigation.ScreenInitNav
import com.tayler.ui.ValuScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<ScreenInitNav.AuthFlow>(startDestination = AuthRoutes.Login) {
        composable<AuthRoutes.Login> {
            ValuScreen(title = "Iniciar Sesión", onBackClick = { navController.popBackStack() }) {
                Text("Pantalla de Login")
                // Ejemplo: Button(onClick = { navController.navigate(AuthRoutes.Register) }) { Text("Ir a Registro") }
            }
        }
        composable<AuthRoutes.Register> {
            ValuScreen(title = "Registro", onBackClick = { navController.popBackStack() }) {
                Text("Pantalla de Registro")
            }
        }
    }
}
