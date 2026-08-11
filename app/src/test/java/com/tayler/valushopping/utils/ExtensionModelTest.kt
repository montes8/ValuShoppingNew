package com.tayler.valushopping.utils

import com.tayler.entity.ProductModel
import com.valu.uitaycompose.utils.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtensionModelTest {

    @Test
    fun `mapperNextProduct returns formatted string`() {
        val product = ProductModel(name = "Product", price = "10", url = "http://img")
        val result = product.mapperNextProduct()
        assertTrue(result.contains("Product"))
        assertTrue(result.contains("http://img"))
    }

    @Test
    fun `mapperCodeSocial returns correct country codes`() {
        assertEquals(COUNTRY_CODE_AR, COUNTRY_AR.mapperCodeSocial())
        assertEquals(COUNTRY_CODE_MX, COUNTRY_MX.mapperCodeSocial())
        assertEquals(COUNTRY_CODE_PE, "OTHER".mapperCodeSocial())
    }
}
