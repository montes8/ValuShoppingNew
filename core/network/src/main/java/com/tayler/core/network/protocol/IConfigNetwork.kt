package com.tayler.core.network.protocol

import com.tayler.core.model.CategoryModel
import com.tayler.core.model.HistoryModel
import com.tayler.core.model.TaskModel
import com.tayler.core.model.UserBlockingModel

interface IConfigNetwork {
    suspend fun loadBlocking(): List<UserBlockingModel>
    suspend fun saveHistory(data : HistoryModel): Boolean
    suspend fun listCategories(): List<CategoryModel>
    suspend fun listCategoriesAll(): List<CategoryModel>
}