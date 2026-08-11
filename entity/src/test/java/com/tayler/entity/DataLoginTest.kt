package com.tayler.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class DataLoginTest {

    @Test
    fun `DataLogin holds correct values`() {
        val user = UserModel(uid = "u1")
        val dataLogin = DataLogin(userValid = user, token = "secret")
        
        assertEquals(user, dataLogin.userValid)
        assertEquals("secret", dataLogin.token)
    }
}
