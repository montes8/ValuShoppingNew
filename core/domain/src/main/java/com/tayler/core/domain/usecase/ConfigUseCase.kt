package com.tayler.core.domain.usecase

import com.tayler.core.model.CategoryModel
import com.tayler.core.model.HistoryModel
import com.tayler.core.network.protocol.IConfigNetwork
import javax.inject.Inject

class ConfigUseCase @Inject constructor(
    private val configNetwork: IConfigNetwork
) {
    suspend fun saveHistory(data: HistoryModel): Boolean {
        return configNetwork.saveHistory(data)
    }

    suspend fun listCategories(): List<CategoryModel> {
        return configNetwork.listCategories()
    }

    suspend fun listCategoriesAll(): List<CategoryModel> {
        return configNetwork.listCategoriesAll()
    }
}
