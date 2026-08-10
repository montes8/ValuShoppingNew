package com.tayler.repository.network.api

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.entity.UserBlockingModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.request.HistoryRequest
import com.tayler.repository.network.model.response.CategoryResponse
import com.tayler.repository.network.model.response.UserBlockingResponse
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.utils.processResponse
import javax.inject.Inject

class ConfigNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IConfigNetwork {

    override suspend fun loadBlocking(): List<UserBlockingModel> = base.safeApiCall {
        try {
            serviceApi.loadUserBlocking().processResponse { UserBlockingResponse.toList(it) }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun saveHistory(data: HistoryModel): Boolean = base.safeApiCall {
        serviceApi.saveHistory(HistoryRequest.toModel(data)).isSuccessful
    }

    override suspend fun listCategories(): List<CategoryModel> = base.safeApiCall {
        serviceApi.loadCategories().processResponse { CategoryResponse.toList(it) }
    }

    override suspend fun listCategoriesAll(): List<CategoryModel> = base.safeApiCall {
        serviceApi.loadCategoriesAll().processResponse { CategoryResponse.toList(it) }
    }
}
