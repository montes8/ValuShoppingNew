package com.tayler.valushopping.ui.home.product

import com.tayler.entity.ParamModel
import com.tayler.entity.ProductModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.utils.distance
import io.mockk.coVerify
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val dataUseCase: DataUseCase = mockk()
    private val appDataVale = AppDataVale()
    private val globalUiStateManager = GlobalUiStateManager()
    private lateinit var viewModel: DataViewModel

    @Before
    fun setUp() {
        mockkStatic("com.tayler.valushopping.utils.ExtensionUtilsKt")
        viewModel = DataViewModel(dataUseCase, appDataVale, globalUiStateManager, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkStatic("com.tayler.valushopping.utils.ExtensionUtilsKt")
    }

    @Test
    fun `loadProductClient filters by distance and updates state`() = runTest(testDispatcher) {
        val mockProducts = listOf(
            ProductModel(uid = "p1", name = "Near", banner = false, latitude = "10.0"),
            ProductModel(uid = "p2", name = "Far", banner = false, latitude = "10.0", limitDistance = "1")
        )
        appDataVale.paramData = ParamModel(limitDistance = "5")
        
        coEvery { dataUseCase.loadProduct(any(), any(), any()) } returns mockProducts
        // Mock distance extension: p1 is close (2.0), p2 is far (10.0)
        every { any<ProductModel>().distance("K", appDataVale) } returnsMany listOf(2.0, 10.0)

        viewModel.loadProductClient(location = true, country = "PE")
        runCurrent()

        val state = viewModel.successLoadProductClientState.value
        // Should only contain p1 because p1 (2.0 < 5) and p2 (10.0 > 1)
        assertEquals(1, state.first.size)
        assertEquals("p1", state.first[0].uid)
        assertTrue(state.third) // isLoaded = true
    }

    @Test
    fun `loadProductClient does not reload if already loaded`() = runTest(testDispatcher) {
        val initialProduct = ProductModel(uid = "exists")
        // Manually set state to "loaded"
        val successState = viewModel.javaClass.getDeclaredField("_successLoadProductClientState").apply {
            isAccessible = true
        }.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<Triple<List<ProductModel>, List<ProductModel>, Boolean>>
        
        successState.value = Triple(listOf(initialProduct), emptyList(), true)

        viewModel.loadProductClient(country = "PE")
        runCurrent()

        coVerify(exactly = 0) { dataUseCase.loadProduct(any(), any(), any()) }
    }

    @Test
    fun `loadProductClient identifies banners correctly`() = runTest(testDispatcher) {
        val mockProducts = listOf(
            ProductModel(uid = "p1", banner = true),
            ProductModel(uid = "p2", banner = false)
        )
        coEvery { dataUseCase.loadProduct(any(), any(), any()) } returns mockProducts
        every { any<ProductModel>().distance(any(), any()) } returns 0.0

        viewModel.loadProductClient(location = false, country = "PE")
        runCurrent()

        val state = viewModel.successLoadProductClientState.value
        assertEquals(1, state.second.size)
        assertEquals("p1", state.second[0].uid)
    }
}
