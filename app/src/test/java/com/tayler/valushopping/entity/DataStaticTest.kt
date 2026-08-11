package com.tayler.valushopping.entity

import org.junit.Assert.assertTrue
import org.junit.Test

class DataStaticTest {

    @Test
    fun `static lists are not empty`() {
        assertTrue(itemsNavBar.isNotEmpty())
        assertTrue(drawerItems.isNotEmpty())
    }
}
