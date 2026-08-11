package com.tayler.valushopping.application

import androidx.lifecycle.LifecycleOwner
import com.tayler.entity.ParamModel
import com.tayler.usecases.AppUseCase
import com.valu.uitaycompose.utils.extension.changeIcon
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationValeTest {

    private lateinit var application: ApplicationVale
    private val appUseCase: AppUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        application = ApplicationVale()
        application.appUseCase = appUseCase
        mockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionConfigKt")
    }

    @After
    fun tearDown() {
        unmockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionConfigKt")
    }

    @Test
    fun `onStop changes icon when different`() = runTest(testDispatcher) {
        val mockParam = ParamModel(idIconOld = "Principal", idIcon = "NewIcon")
        every { appUseCase.configInitParam() } returns mockParam
        every { appUseCase.saveParam(any()) } returns Unit
        // Mock changeIcon top-level function
        every { any<ApplicationVale>().changeIcon(any(), any()) } returns Unit

        application.onStop(mockk<LifecycleOwner>())
        advanceUntilIdle()

        verify { appUseCase.saveParam(match { it.idIconOld == "NewIcon" }) }
        verify { application.changeIcon("NewIcon", "Principal") }
    }

    @Test
    fun `onStop does not change icon when same`() = runTest(testDispatcher) {
        val mockParam = ParamModel(idIconOld = "Same", idIcon = "Same")
        every { appUseCase.configInitParam() } returns mockParam

        application.onStop(mockk<LifecycleOwner>())
        advanceUntilIdle()

        verify(exactly = 0) { appUseCase.saveParam(any()) }
    }
}
