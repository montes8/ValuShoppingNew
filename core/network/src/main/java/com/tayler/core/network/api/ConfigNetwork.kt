package com.tayler.core.network.api

import com.tayler.core.model.CategoryModel
import com.tayler.core.model.HistoryModel
import com.tayler.core.model.UserBlockingModel
import com.tayler.core.network.api.ServiceApi
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.data.api.model.HistoryRequest
import com.tayler.core.network.data.api.model.CategoryResponse
import com.tayler.core.network.data.api.model.UserBlockingResponse
import com.tayler.core.network.protocol.IConfigNetwork
import com.tayler.core.network.utils.processResponse
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
