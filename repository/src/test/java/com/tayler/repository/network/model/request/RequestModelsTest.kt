package com.tayler.repository.network.model.request

import com.tayler.entity.HistoryModel
import com.tayler.entity.ProductImageModel
import org.junit.Assert.assertEquals
import org.junit.Test

class RequestModelsTest {

    @Test
    fun `HistoryRequest toModel mapping works correctly`() {
        val entity = HistoryModel(
            type = "t", name = "n", latitude = "1", longitude = "2",
            address = "a", imei = "i", identifier = "id", date = "d",
            hour = "h", ipAddress = "ip", numberPhone = "p"
        )
        val request = HistoryRequest.toModel(entity)
        assertEquals("t", request.type)
        assertEquals("n", request.name)
        assertEquals("ip", request.ipAddress)
    }

    @Test
    fun `LoginRequest holds correct values`() {
        val request = LoginRequest(nameUser = "user", password = "pass")
        assertEquals("user", request.nameUser)
        assertEquals("pass", request.password)
    }

    @Test
    fun `ProductImageRequest toModel mapping works correctly`() {
        val entity = ProductImageModel(idProduct = "p1", name = "img")
        val request = ProductImageRequest.toModel(entity)
        assertEquals("p1", request.idProduct)
        assertEquals("img", request.name)
    }
}
