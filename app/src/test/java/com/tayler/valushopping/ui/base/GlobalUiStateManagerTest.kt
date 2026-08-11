package com.tayler.valushopping.ui.base

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GlobalUiStateManagerTest {

    @Test
    fun `updateUiState updates the state flow`() {
        val manager = GlobalUiStateManager()
        manager.updateUiState { it.copy(loading = true, statusBarColor = Color.Red) }
        
        assertTrue(manager.uiState.value.loading)
        assertEquals(Color.Red, manager.uiState.value.statusBarColor)
    }
}
