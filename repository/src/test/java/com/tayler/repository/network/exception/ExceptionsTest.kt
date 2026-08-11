package com.tayler.repository.network.exception

import com.tayler.entity.exception.UiTayApiException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExceptionsTest {

    @Test
    fun `getApiException returns correctly mapped UiTayApiException`() {
        val errorModel = CompleteErrorModel(code = 404, title = "Not Found", description = "Page not found")
        val apiException = errorModel.getApiException()
        
        assertTrue(apiException is UiTayApiException)
        val uiException = apiException as UiTayApiException
        assertEquals(404, uiException.code)
        assertEquals("Not Found", uiException.title)
        assertEquals("Page not found", uiException.messageApi)
    }

    @Test
    fun `getApiException handles null fields with defaults`() {
        val errorModel = CompleteErrorModel(code = null, title = null, description = null)
        val apiException = errorModel.getApiException() as UiTayApiException
        
        assertEquals(0, apiException.code)
        // Check if it uses general error title/message from constants
        // Based on the source code, it uses ERROR_MESSAGE_GENERAL for both title and description if null
        assertNotNull(apiException.title)
        assertNotNull(apiException.messageApi)
    }
}
