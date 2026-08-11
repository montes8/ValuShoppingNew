package com.tayler.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class EntityModelsTest {

    @Test
    fun `CategoryModel holds correct values`() {
        val model = CategoryModel(uid = "1", name = "Cat")
        assertEquals("1", model.uid)
        assertEquals("Cat", model.name)
    }

    @Test
    fun `HistoryModel holds correct values`() {
        val model = HistoryModel(
            type = "click",
            name = "Tayler",
            latitude = "1.0",
            longitude = "2.0",
            address = "addr",
            imei = "123",
            identifier = "id",
            date = "today",
            hour = "now",
            ipAddress = "ip",
            numberPhone = "999"
        )
        assertEquals("click", model.type)
        assertEquals("Tayler", model.name)
    }

    @Test
    fun `ImageModel holds correct values`() {
        val model = ImageModel(nameImage = "img")
        assertEquals("img", model.nameImage)
    }

    @Test
    fun `ImageMoreModel holds correct values`() {
        val model = ImageMoreModel(uid = "1", name = "img", idProduct = "p1", idUser = "u1", url = "url", nameFile = "file")
        assertEquals("1", model.uid)
        assertEquals("img", model.name)
    }

    @Test
    fun `ProductImageModel holds correct values`() {
        val model = ProductImageModel(idProduct = "p1", name = "img")
        assertEquals("p1", model.idProduct)
        assertEquals("img", model.name)
    }

    @Test
    fun `TaskModel holds correct values`() {
        val model = TaskModel(uid = "1", issue = "Pollution")
        assertEquals("1", model.uid)
        assertEquals("Pollution", model.issue)
    }

    @Test
    fun `UserBlockingModel holds correct values`() {
        val model = UserBlockingModel(imei = "123", name = "Banned")
        assertEquals("123", model.imei)
        assertEquals("Banned", model.name)
    }
}
