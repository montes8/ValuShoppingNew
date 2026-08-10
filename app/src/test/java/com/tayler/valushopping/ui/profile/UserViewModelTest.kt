package com.tayler.valushopping.ui.profile

import app.cash.turbine.test
import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import io.mockk.coEvery
import io.mockk.every
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
class UserViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val appUseCase: AppUseCase = mockk()
    private val globalUiStateManager = GlobalUiStateManager()
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        viewModel = UserViewModel(appUseCase, globalUiStateManager, testDispatcher)
    }

    @Test
    fun loadUser_getsUserCorrectlly() = runTest(testDispatcher) {
        val mockUser = UserModel(names = "Tayler")
        every { appUseCase.getUser() } returns mockUser

        viewModel.loadUser()
        runCurrent()

        assertEquals(mockUser, viewModel.formState.value)
    }

    @Test
    fun saveUser_savesUserAndUpdateState() = runTest(testDispatcher) {
        val userToSave = UserModel(names = "New Name")
        every { appUseCase.saveUser(userToSave) } returns userToSave

        viewModel.saveUser(userToSave)
        runCurrent()

        assertEquals(userToSave, viewModel.formState.value)
    }
}
