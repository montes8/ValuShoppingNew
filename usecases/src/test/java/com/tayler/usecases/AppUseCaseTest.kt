package com.tayler.usecases

import com.tayler.entity.ParamModel
import com.tayler.entity.UserBlockingModel
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.network.protocol.IUserNetwork
import com.tayler.repository.preferences.IAppPreferences
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppUseCaseTest {

    private lateinit var appUseCase: AppUseCase
    private val appPreferences: IAppPreferences = mockk(relaxed = true)
    private val userNetwork: IUserNetwork = mockk()
    private val configNetwork: IConfigNetwork = mockk()

    @Before
    fun setUp() {
        appUseCase = AppUseCase(appPreferences, userNetwork, configNetwork)
    }

    @Test
    fun `paramInit saves UUID if empty`() = runTest {
        every { appPreferences.getUUID() } returns ""
        coEvery { userNetwork.loadParam(any()) } returns ParamModel()
        coEvery { configNetwork.loadBlocking() } returns emptyList()

        appUseCase.paramInit("imei", "code")

        verify { appPreferences.saveUUID(any()) }
    }

    @Test
    fun `paramInit validates blocking correctly`() = runTest {
        val imei = "12345"
        val code = "abc"
        val blockingList = listOf(UserBlockingModel(imei = imei))
        
        every { appPreferences.getUUID() } returns "uuid"
        coEvery { userNetwork.loadParam(code) } returns ParamModel()
        coEvery { configNetwork.loadBlocking() } returns blockingList

        val result = appUseCase.paramInit(imei, code)

        assertTrue(result.blocking == true)
        verify { appPreferences.saveParaDb(any()) }
    }

    @Test
    fun `validateBlocking returns true when imei matches`() {
        val imei = "123"
        val list = listOf(UserBlockingModel(imei = imei))
        every { appPreferences.getUUID() } returns "other"

        val result = appUseCase.validateBlocking(list, imei)

        assertTrue(result)
    }

    @Test
    fun `validateBlocking returns true when identifierId matches UUID`() {
        val uuid = "uuid-123"
        val list = listOf(UserBlockingModel(identifierId = uuid))
        every { appPreferences.getUUID() } returns uuid

        val result = appUseCase.validateBlocking(list, "other-imei")

        assertTrue(result)
    }

    @Test
    fun `validateBlocking returns false when no match`() {
        val list = listOf(UserBlockingModel(imei = "1", identifierId = "a"))
        every { appPreferences.getUUID() } returns "b"

        val result = appUseCase.validateBlocking(list, "2")

        assertFalse(result)
    }

    @Test
    fun `configInitParam returns param with session and urlImage`() {
        val mockParam = ParamModel(title = "Test")
        every { appPreferences.getUUID() } returns "uuid"
        every { appPreferences.getParaDb() } returns mockParam
        every { appPreferences.getToken() } returns true // Assuming session is Boolean based on ParamModel

        val result = appUseCase.configInitParam()

        assertEquals("Test", result.title)
        assertTrue(result.session)
    }
}
