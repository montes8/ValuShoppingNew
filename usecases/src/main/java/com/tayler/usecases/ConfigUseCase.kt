package com.tayler.usecases

import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.entity.TaskModel
import com.tayler.entity.UserBlockingModel
import com.tayler.repository.network.protocol.IConfigNetwork
import jakarta.inject.Inject

class ConfigUseCase@Inject constructor(
    private val iConfigNetwork: IConfigNetwork
){
    suspend fun loadBlocking(): List<UserBlockingModel>{
        return iConfigNetwork.loadBlocking()
    }

    suspend fun saveHistory(data : HistoryModel): Boolean{
        return iConfigNetwork.saveHistory(data)
    }

    suspend fun listTask(): List<TaskModel>{
        return iConfigNetwork.listTask()
    }

    suspend fun listCategories(): List<CategoryModel>{
        return iConfigNetwork.listCategories()
    }
    suspend fun listCategoriesAll(): List<CategoryModel>{
        return iConfigNetwork.listCategoriesAll()
    }
}