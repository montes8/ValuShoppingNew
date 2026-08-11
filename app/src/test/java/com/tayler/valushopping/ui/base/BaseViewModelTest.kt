package com.tayler.valushopping.ui.base

import com.tayler.valushopping.rule.MainDispatcherRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var viewModel: BaseViewModel
    private val globalUiStateManager = GlobalUiStateManager()

    @Before
    fun setUp() {
        viewModel = BaseViewModel(testDispatcher)
    }

    @Test
    fun `execute updates loading state correctly`() = runTest(testDispatcher) {
        viewModel.execute(loading = true) {
            // Mientras se ejecuta func(), el estado debe ser loading
            assertTrue(viewModel.uiStateBase.value.loading)
        }
        runCurrent()
        // Al terminar, loading debe ser false
        assertFalse(viewModel.uiStateBase.value.loading)
    }

    @Test
    fun `execute handles exceptions and updates error state`() = runTest(testDispatcher) {
        val exception = RuntimeException("Test Error")
        
        viewModel.execute {
            throw exception
        }
        runCurrent()

        assertTrue(viewModel.uiStateBase.value.error)
        assertEquals(exception, viewModel.uiStateBase.value.errorType)
        assertFalse(viewModel.uiStateBase.value.loading)
    }

    @Test
    fun `executeState updates target flow and handles loading`() = runTest(testDispatcher) {
        val targetFlow = MutableStateFlow<String?>(null)
        
        viewModel.executeState(targetFlow) {
            "result"
        }
        runCurrent()

        assertEquals("result", targetFlow.value)
        assertFalse(viewModel.uiStateBase.value.loading)
    }

    @Test
    fun `updateUiState updates state correctly`() {
        viewModel.updateUiState { it.copy(loading = true) }
        assertTrue(viewModel.uiStateBase.value.loading)
    }
}
