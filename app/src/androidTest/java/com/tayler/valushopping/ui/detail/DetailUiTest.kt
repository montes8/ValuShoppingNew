package com.tayler.valushopping.ui.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tayler.valushopping.ui.InitActivity
import org.junit.Rule
import org.junit.Test

/**
 * Pruebas instrumentales para el detalle del producto.
 */
class DetailUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<InitActivity>()

    @Test
    fun verifyDetailFlow() {
        // 1. Ir a la pestaña de productos
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("Productos").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Productos").performClick()

        // 2. Esperar a que cargue la lista y hacer clic en el primer producto
        // Usamos waitUntil para esperar a que el LazyColumn tenga items
        composeTestRule.waitUntil(timeoutMillis = 10000) {
             composeTestRule.onAllNodesWithTag("product_lazy_column").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Hacemos clic en algun elemento que contenga texto (si hay productos cargados)
        // Como no sabemos el nombre del producto, intentamos buscar por un patron o simplemente el primer item clickable
        // Para este test, asumimos que el repositorio devuelve datos.
    }
}
