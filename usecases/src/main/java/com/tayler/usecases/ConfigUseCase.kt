package com.tayler.usecases

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.repository.network.protocol.IConfigNetwork
import jakarta.inject.Inject

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
