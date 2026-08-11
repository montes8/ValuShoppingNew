package com.tayler.valushopping.ui.profile

import app.cash.turbine.test
import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

    @Test
    fun `form field changes update state correctly`() = runTest(testDispatcher) {
        viewModel.onNameChanged("Name")
        assertEquals("Name", viewModel.formState.value.names)

        viewModel.onLastNameChanged("Last")
        assertEquals("Last", viewModel.formState.value.lastName)

        viewModel.onDocumentChanged("123")
        assertEquals("123", viewModel.formState.value.document)

        viewModel.onEmailChanged("email@test.com")
        assertEquals("email@test.com", viewModel.formState.value.email)

        viewModel.onPhoneChanged("999")
        assertEquals("999", viewModel.formState.value.phone)

        viewModel.onAddressChanged("Addr")
        assertEquals("Addr", viewModel.formState.value.address)
    }

    @Test
    fun `form validation logic works`() = runTest(testDispatcher) {
        viewModel.isFormValid.test {
            assertFalse(awaitItem()) // Initial

            // Fill all fields validly
            viewModel.onNameChanged("Tayler")
            viewModel.onLastNameChanged("Montes")
            viewModel.onDocumentChanged("12345678") // 8 digits
            viewModel.onEmailChanged("tayler@test.com")
            viewModel.onPhoneChanged("999888777")
            viewModel.onAddressChanged("Calle Viva 123")
            
            assertTrue(awaitItem())
        }
    }

    @Test
    fun `saveUserImg updates state`() = runTest(testDispatcher) {
        val user = UserModel(names = "ImgUser")
        every { appUseCase.saveUser(user) } returns user
        
        viewModel.saveUserImg(user)
        runCurrent()
        
        assertEquals(user, viewModel.formState.value)
    }
}
