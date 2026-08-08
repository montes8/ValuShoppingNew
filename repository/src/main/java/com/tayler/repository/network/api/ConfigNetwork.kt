package com.tayler.repository.network.api

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.entity.TaskModel
import com.tayler.entity.UserBlockingModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.request.HistoryRequest
import com.tayler.repository.network.model.response.CategoryResponse
import com.tayler.repository.network.model.response.TaskResponse
import com.tayler.repository.network.model.response.UserBlockingResponse
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.utils.EMPTY_VALE
import com.tayler.repository.utils.processResponse
import javax.inject.Inject

class ConfigNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IConfigNetwork {

    override suspend fun loadBlocking(): List<UserBlockingModel> = base.safeApiCall {
        try {
            serviceApi.loadUserBlocking().processResponse { UserBlockingResponse.toList(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun saveHistory(data: HistoryModel): Boolean = base.safeApiCall {
        serviceApi.saveHistory(HistoryRequest.toModel(data)).isSuccessful
    }

    override suspend fun listTask(): List<TaskModel> = base.safeApiCall {
        try {
            serviceApi.loadTaskValu().processResponse { TaskResponse.toList(it) }
        } catch (e: Exception) {
            listOf(
                TaskModel(
                    uid = EMPTY_VALE,
                    course = "COMUNICACION",
                    issue = "contaminacion ambiental de mi localidad (comas)",
                    concept = EMPTY_VALE,
                    titleOne = "Causas",
                    conceptOne = "Basura Acumulada en las calles,\nQuema de basura en las calle,\n" +
                            "Humo de los vehiculos y el ruido execesivo,\nPersonas que botan basura en la calle",
                    titleTwo = "Efectos",
                    conceptTwo = "Causa daño en la salud:\nPuede causar enfermendades respiratoria y hasta cancer\n" +
                            "Ejemplo:puede causar asma o bronquitis cronica",
                    titleThree = "Solucion",
                    conceptThree = "Se puede aumentar mas camiones de basura,y tambien se puede hacer campañas para orientar" +
                            "como no contaminar el medio ambiente"
                )
            )
        }
    }

    override suspend fun listCategories(): List<CategoryModel> = base.safeApiCall {
        serviceApi.loadCategories().processResponse { CategoryResponse.toList(it) }
    }

    override suspend fun listCategoriesAll(): List<CategoryModel> = base.safeApiCall {
        serviceApi.loadCategoriesAll().processResponse { CategoryResponse.toList(it) }
    }
}
