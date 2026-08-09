package com.tayler.valushopping.ui.base

import app.cash.turbine.test
import com.tayler.valushopping.rule.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    private val testDispatcher = kotlinx.coroutines.test.StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var viewModel: BaseViewModel

    @org.junit.Before
    fun setUp() {
        viewModel = BaseViewModel(testDispatcher)
    }

    @Test
    fun execute_setsLoadingAndShimmerCorrectlly() = runTest(testDispatcher) {
        viewModel.uiStateBase.test {
            // Initial state
            var state = awaitItem()
            assertFalse(state.loading)

            viewModel.execute(loading = true) {
                // Inside func
            }
            
            runCurrent()

            // Loading state
            state = awaitItem()
            assertTrue(state.loading)
            assertTrue(state.shimmer)

            runCurrent()

            // Final state
            state = awaitItem()
            assertFalse(state.loading)
            assertFalse(state.shimmer)
        }
    }

    @Test
    fun execute_handlesExceptionsAndSetsErrorState() = runTest(testDispatcher) {
        val exception = RuntimeException("Test error")
        
        viewModel.uiStateBase.test {
            awaitItem() // Initial

            viewModel.execute {
                throw exception
            }
            
            runCurrent()

            assertTrue(awaitItem().loading) // Loading
            
            val errorState = awaitItem()
            assertTrue(errorState.error)
            assertEquals(exception, errorState.errorType)

            assertFalse(awaitItem().loading) // Final from finally block
        }
    }

    @Test
    fun executeState_updatesProvidedStateFlow() = runTest(testDispatcher) {
        val resultFlow = MutableStateFlow<String?>(null)
        val expectedValue = "Success"

        viewModel.executeState(resultFlow) {
            expectedValue
        }
        
        runCurrent()

        assertEquals(expectedValue, resultFlow.value)
    }

    @Test
    fun execute_updatesGlobalUiStateManagerIfProvided() = runTest(testDispatcher) {
        val globalUiStateManager = GlobalUiStateManager()
        
        globalUiStateManager.uiState.test {
            awaitItem() // Initial

            viewModel.execute(globalUiStateManager = globalUiStateManager) {
                // Do nothing
            }
            
            runCurrent()

            assertTrue(awaitItem().loading) // Loading
            assertFalse(awaitItem().loading) // Final
        }
    }
}
