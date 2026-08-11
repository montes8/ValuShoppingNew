package com.tayler.valushopping.ui.detail

import com.tayler.entity.ImageMoreModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val dataUseCase: DataUseCase = mockk()
    private val globalUiStateManager = GlobalUiStateManager()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = DetailViewModel(dataUseCase, globalUiStateManager, testDispatcher)
    }

    @Test
    fun `loadMoreImageProduct updates state with results`() = runTest(testDispatcher) {
        val mockImages = listOf(ImageMoreModel(uid = "i1", name = "img1", idProduct = "p1", idUser = "u1", url = "url", nameFile = "file"))
        coEvery { dataUseCase.loadProductImage("p1") } returns mockImages

        viewModel.loadMoreImageProduct("p1")
        runCurrent()

        assertEquals(mockImages, viewModel.successProductImageState.value)
    }
}
