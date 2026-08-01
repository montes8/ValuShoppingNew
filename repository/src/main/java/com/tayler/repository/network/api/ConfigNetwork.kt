package com.tayler.repository.network.api

import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.request.HistoryRequest
import com.tayler.repository.network.model.response.CategoryResponse
import com.tayler.repository.network.model.response.TaskResponse
import com.tayler.repository.network.model.response.UserBlockingResponse
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.utils.EMPTY_VALE
import com.tayler.repository.utils.validateBody
import com.tayler.repository.utils.validateData
import javax.inject.Inject

class ConfigNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IConfigNetwork {
    override suspend fun loadBlocking(): List<UserBlockingResponse> {
        return base.executeWithConnection(false) {
            var model: List<UserBlockingResponse>? = null
            val response =  serviceApi.loadUserBlocking()
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: ArrayList()
        }
    }

    override suspend fun saveHistory(data: HistoryRequest): Boolean {
        return base.executeWithConnection {
            val response = serviceApi.saveHistory(data)
            response.validateData()
        }
    }

    override suspend fun listTask(): List<TaskResponse> {
        return base.executeWithConnection(false) {
            var model: List<TaskResponse>? = null
            val response =  serviceApi.loadTaskValu()
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: arrayListOf(
                TaskResponse(
                    uid = EMPTY_VALE,
                    course = "COMUNICACION",
                    issue= "contaminacion ambiental de mi localidad (comas)",
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

    override suspend fun listCategories(): List<CategoryResponse> {
        return base.executeWithConnection{
            var model: List<CategoryResponse>? = null
            val response =  serviceApi.loadCategories()
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: arrayListOf()
        }
    }

    override suspend fun listCategoriesAll(): List<CategoryResponse> {
        return base.executeWithConnection{
            var model: List<CategoryResponse>? = null
            val response =  serviceApi.loadCategoriesAll()
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: arrayListOf()
        }
    }

}