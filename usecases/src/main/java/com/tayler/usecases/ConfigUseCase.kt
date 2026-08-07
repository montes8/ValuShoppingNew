package com.tayler.usecases

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.repository.network.protocol.IConfigNetwork
import jakarta.inject.Inject

class ConfigUseCase@Inject constructor(
    private val iConfigNetwork: IConfigNetwork
){
    suspend fun saveHistory(data : HistoryModel): Boolean{
        return iConfigNetwork.saveHistory(data)
    }

    suspend fun listCategories(): List<CategoryModel>{
        return iConfigNetwork.listCategories()
    }
    suspend fun listCategoriesAll(): List<CategoryModel>{
        return iConfigNetwork.listCategoriesAll()
    }
}