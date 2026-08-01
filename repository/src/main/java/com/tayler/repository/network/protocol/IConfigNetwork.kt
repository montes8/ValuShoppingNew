package com.tayler.repository.network.protocol

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.entity.TaskModel
import com.tayler.entity.UserBlockingModel

interface IConfigNetwork {
    suspend fun loadBlocking(): List<UserBlockingModel>
    suspend fun saveHistory(data : HistoryModel): Boolean
    suspend fun listTask(): List<TaskModel>
    suspend fun listCategories(): List<CategoryModel>
    suspend fun listCategoriesAll(): List<CategoryModel>
}