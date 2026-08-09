package com.tayler.valushopping.ui.about

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tayler.valushopping.ui.InitActivity
import org.junit.Rule
import org.junit.Test

/**
 * Pruebas instrumentales para la pantalla "Sobre Nosotros".
 */
class AboutUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<InitActivity>()

    @Test
    fun verifyAboutScreen() {
        // 1. Esperamos a que la Home este lista
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("VALU SHOOPING").fetchSemanticsNodes().isNotEmpty()
        }

        // 2. Abrimos el Drawer
        composeTestRule.onNodeWithTag("home_top_bar").performClick()

        // 3. Hacemos clic en "Sobre nostros" (segun strings.xml)
        composeTestRule.onNodeWithText("Sobre nostros").performClick()

        // 4. Verificamos contenido
        composeTestRule.onNodeWithTag("about_scroll_content").assertIsDisplayed()
        composeTestRule.onNodeWithText("Modo de uso").assertIsDisplayed()
    }
}
