package com.tayler.valushopping.ui.splash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.tayler.valushopping.ui.InitActivity
import org.junit.Rule
import org.junit.Test

/**
 * Pruebas instrumentales para la pantalla de Splash.
 * Se ejecutan en un dispositivo real o emulador para validar la interfaz de usuario.
 */
class SplashUiTest {

    // Regla que lanza la actividad de inicio
    @get:Rule
    val composeTestRule = createAndroidComposeRule<InitActivity>()

    @Test
    fun verifyBasicSplashElements() {
        // 1. Buscamos el logo de la bolsa por su tag de prueba ("splash_bag_image")
        // El test fallara si el elemento no es visible en la pantalla
        composeTestRule
            .onNodeWithTag("splash_bag_image")
            .assertIsDisplayed()

        // 2. Tambien buscamos por descripcion de contenido para asegurar accesibilidad
        composeTestRule
            .onNodeWithContentDescription("Logo de la bolsa")
            .assertIsDisplayed()
    }

    @Test
    fun verifyScreenStructure() {
        // Aseguramos que la jerarquia de Compose se haya cargado correctamente
        // buscando el elemento raiz si tuviera un tag, o simplemente validando el logo
        composeTestRule.onNodeWithTag("splash_bag_image").assertExists()
    }
}
