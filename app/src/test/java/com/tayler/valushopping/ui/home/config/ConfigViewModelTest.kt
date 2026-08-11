package com.tayler.valushopping.ui.home.config

import android.content.Context
import com.tayler.entity.ParamModel
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.utils.JSON_ITEM
import com.tayler.valushopping.utils.JSON_ITEM_ADMIN
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConfigViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val appDataVale = AppDataVale()
    private val context: Context = mockk()
    private lateinit var viewModel: ConfigViewModel

    @Before
    fun setUp() {
        // Pasamos el testDispatcher para IO y Default
        viewModel = spyk(ConfigViewModel(appDataVale, testDispatcher, testDispatcher))
    }

    @Test
    fun `loadConfigData loads items when state is empty`() = runTest(testDispatcher) {
        val mockItems = listOf(ItemModel(title = "Item 1"))
        appDataVale.paramData = ParamModel(session = false)
        every { viewModel.loadJsonData(context, JSON_ITEM) } returns mockItems

        viewModel.loadConfigData(context)
        advanceUntilIdle()

        assertEquals(mockItems, viewModel.itemsState.value)
    }

    @Test
    fun `loadConfigData loads admin items when session is true`() = runTest(testDispatcher) {
        val mockItems = listOf(ItemModel(title = "Admin Item"))
        appDataVale.paramData = ParamModel(session = true)
        every { viewModel.loadJsonData(context, JSON_ITEM_ADMIN) } returns mockItems

        viewModel.loadConfigData(context)
        advanceUntilIdle()

        assertEquals(mockItems, viewModel.itemsState.value)
    }

    @Test
    fun `loadConfigData returns early if items already exist`() = runTest(testDispatcher) {
        val initialItems = listOf(ItemModel(title = "Exist"))
        every { viewModel.loadJsonData(context, any()) } returns initialItems
        
        viewModel.loadConfigData(context)
        advanceUntilIdle()
        assertEquals(1, viewModel.itemsState.value.size)
        
        viewModel.loadConfigData(context) // Second call
        advanceUntilIdle()
        
        io.mockk.verify(exactly = 1) { viewModel.loadJsonData(any(), any()) }
    }

    @Test
    fun `loadConfigData handles exception by setting empty list`() = runTest(testDispatcher) {
        appDataVale.paramData = ParamModel(session = false)
        every { viewModel.loadJsonData(context, any()) } throws RuntimeException("Error")

        viewModel.loadConfigData(context)
        advanceUntilIdle()

        assertTrue(viewModel.itemsState.value.isEmpty())
    }
}
