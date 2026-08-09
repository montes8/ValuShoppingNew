package com.tayler.valushopping.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tayler.valushopping.ui.InitActivity
import org.junit.Rule
import org.junit.Test

/**
 * Pruebas instrumentales para la pantalla principal (Home).
 * Valida la navegacion entre las pestañas del BottomBar.
 */
class HomeUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<InitActivity>()

    @Test
    fun verifyHomeNavigation() {
        // 1. Esperamos a que pase el Splash y se cargue la Home (Init)
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithTag("screen_init_content").fetchSemanticsNodes().isNotEmpty()
        }

        // 2. Verificamos que el contenido de Inicio es visible
        composeTestRule.onNodeWithTag("screen_init_content").assertIsDisplayed()

        // 3. Navegamos a "Productos"
        composeTestRule.onNodeWithText("Productos").performClick()
        composeTestRule.onNodeWithTag("product_lazy_column").assertIsDisplayed()

        // 4. Navegamos a "Categorias"
        composeTestRule.onNodeWithText("Categorias").performClick()
        composeTestRule.onNodeWithTag("category_grid").assertIsDisplayed()

        // 5. Navegamos a "Config"
        composeTestRule.onNodeWithText("Config").performClick()
        composeTestRule.onNodeWithTag("config_lazy_column").assertIsDisplayed()
    }
}
