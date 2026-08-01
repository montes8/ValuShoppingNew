package com.tayler.repository.network.protocol

interface IConfigNetwork {
    suspend fun loadBlocking(): List<UserBlockingModel>
    suspend fun saveHistory(data : HistoryModel): Boolean

    suspend fun listTask(): List<TaskModel>

    suspend fun listCategories(): List<CategoryModel>

    suspend fun listCategoriesAll(): List<CategoryModel>

}