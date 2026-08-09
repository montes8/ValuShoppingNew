package com.tayler.repository.preferences.api

import com.tayler.entity.UserModel
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.toJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AppPreferencesTest {

    private lateinit var appPreferences: AppPreferences
    private val preferencesManager: PreferencesManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        appPreferences = AppPreferences(preferencesManager)
    }

    @Test
    fun saveUser_savesUserAsJsonInPreferences() {
        // Preparación: Definimos un usuario y su JSON esperado
        val user = UserModel(uid = "u1", names = "Tayler")
        val userJson = user.toJson()
        every { preferencesManager.getString(any()) } returns userJson

        // Acción: Guardamos el usuario
        appPreferences.saveUser(user)

        // Verificación: Comprobamos que se llamó al manager con el JSON correcto
        verify { preferencesManager.setValue(any(), userJson) }
    }

    @Test
    fun getUser_returnsUserModelFromStoredJson() {
        // Preparación: Simulamos un JSON guardado
        val user = UserModel(uid = "u1", names = "Tayler")
        every { preferencesManager.getString(any()) } returns user.toJson()

        // Acción: Obtenemos el usuario
        val result = appPreferences.getUser()

        // Verificación: El modelo resultante debe ser igual al original
        assertEquals(user.uid, result.uid)
        assertEquals(user.names, result.names)
    }
}
