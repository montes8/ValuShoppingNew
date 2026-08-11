package com.tayler.repository.preferences.api

import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.PREFERENCE_PARAM
import com.tayler.repository.utils.PREFERENCE_TOKEN
import com.tayler.repository.utils.PREFERENCE_USER
import com.tayler.repository.utils.PREFERENCE_UUID
import com.tayler.repository.utils.toJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppPreferencesTest {

    private lateinit var appPreferences: AppPreferences
    private val preferenceManager: PreferencesManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        appPreferences = AppPreferences(preferenceManager)
    }

    @Test
    fun saveToken_callsPreferenceManager() {
        appPreferences.saveToken("test-token")
        verify { preferenceManager.setValue(PREFERENCE_TOKEN, "test-token") }
    }

    @Test
    fun getToken_returnsTrueWhenNotEmpty() {
        every { preferenceManager.getString(PREFERENCE_TOKEN) } returns "token"
        assertTrue(appPreferences.getToken())

        every { preferenceManager.getString(PREFERENCE_TOKEN) } returns ""
        assertFalse(appPreferences.getToken())
    }

    @Test
    fun saveUUID_callsPreferenceManager() {
        appPreferences.saveUUID("test-uuid")
        verify { preferenceManager.setValue(PREFERENCE_UUID, "test-uuid") }
    }

    @Test
    fun getUUID_returnsValueFromPreferenceManager() {
        every { preferenceManager.getString(PREFERENCE_UUID) } returns "test-uuid"
        assertEquals("test-uuid", appPreferences.getUUID())
    }

    @Test
    fun saveUser_savesJsonAndReturnsUser() {
        val user = UserModel(uid = "u1", names = "Tayler")
        every { preferenceManager.getString(PREFERENCE_USER) } returns user.toJson()

        val result = appPreferences.saveUser(user)

        verify { preferenceManager.setValue(PREFERENCE_USER, user.toJson()) }
        assertEquals(user, result)
    }

    @Test
    fun getUser_returnsDefaultWhenEmpty() {
        every { preferenceManager.getString(PREFERENCE_USER) } returns ""
        val result = appPreferences.getUser()
        assertEquals(UserModel(), result)
    }

    @Test
    fun saveParaDb_savesJsonAndReturnsParam() {
        val param = ParamModel(title = "Test")
        every { preferenceManager.getString(PREFERENCE_PARAM) } returns param.toJson()

        val result = appPreferences.saveParaDb(param)

        verify { preferenceManager.setValue(PREFERENCE_PARAM, param.toJson()) }
        assertEquals(param, result)
    }

    @Test
    fun getParaDb_returnsDefaultWhenEmpty() {
        every { preferenceManager.getString(PREFERENCE_PARAM) } returns ""
        val result = appPreferences.getParaDb()
        assertEquals(ParamModel(), result)
    }
}
