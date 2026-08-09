package com.tayler.valushopping.ui.profile

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
 * Pruebas instrumentales para la pantalla de Perfil.
 * Valida que se pueda acceder desde el menu lateral y ver los datos.
 */
class ProfileUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<InitActivity>()

    @Test
    fun verifyProfileScreen() {
        // 1. Esperamos a que la Home este lista
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithText("VALU SHOOPING").fetchSemanticsNodes().isNotEmpty()
        }

        // 2. Abrimos el Drawer (Menu lateral) usando el tag de la barra superior
        composeTestRule.onNodeWithTag("home_top_bar").performClick()

        // 3. Hacemos clic en la opcion de "Perfil" (usando el texto del recurso)
        // El texto exacto es "Perfil" segun strings.xml
        composeTestRule.onNodeWithText("Perfil").performClick()

        // 4. Verificamos que estamos en la pantalla de perfil
        composeTestRule.onNodeWithTag("profile_scroll_content").assertIsDisplayed()
        composeTestRule.onNodeWithText("Datos Personales").assertIsDisplayed()
    }
}
