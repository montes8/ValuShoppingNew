package com.tayler.usecases

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.repository.network.protocol.IConfigNetwork
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConfigUseCaseTest {

    private lateinit var configUseCase: ConfigUseCase
    private val configNetwork: IConfigNetwork = mockk()

    @Before
    fun setUp() {
        configUseCase = ConfigUseCase(configNetwork)
    }

    @Test
    fun saveHistory_delegatesToRepositoryWithAllFields() = runTest {
        // Preparación: Creamos un modelo de historia con datos completos
        val history = HistoryModel(
            type = "click",
            name = "Tayler",
            latitude = "0.0",
            longitude = "0.0",
            address = "Av. Siempre Viva",
            imei = "12345",
            identifier = "uuid",
            date = "2024-01-01",
            hour = "12:00",
            ipAddress = "127.0.0.1",
            numberPhone = "999999999"
        )
        coEvery { configNetwork.saveHistory(history) } returns true

        // Acción: Ejecutamos el guardado de historia
        val result = configUseCase.saveHistory(history)

        // Verificación: Comprobamos el éxito y la interacción con el repositorio
        assertTrue(result)
        coVerify { configNetwork.saveHistory(history) }
    }

    @Test
    fun listCategories_returnsListFromRepositoryCorrectlly() = runTest {
        // Preparación: Definimos una lista de categorías de prueba
        val mockList = listOf(CategoryModel(uid = "1", name = "Cat"))
        coEvery { configNetwork.listCategories() } returns mockList

        // Acción: Obtenemos las categorías
        val result = configUseCase.listCategories()

        // Verificación: Comprobamos la integridad de los datos
        assertEquals(mockList, result)
    }
}
