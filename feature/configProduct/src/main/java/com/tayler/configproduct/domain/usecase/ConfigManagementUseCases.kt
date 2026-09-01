package com.tayler.configproduct.domain.usecase

import com.tayler.core.model.ParamModel
import com.tayler.configproduct.domain.repository.ConfigProductRepository
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: ConfigProductRepository) {
    suspend fun uploadPrincipal(file: File?, name: String) = repository.saveImage(file, name)
    suspend fun uploadMore(file: File?, name: String) = repository.saveImageMore(file, name)
}

class GetAppParamsUseCase @Inject constructor(private val repository: ConfigProductRepository) {
    suspend operator fun invoke(id: String) = repository.loadParam(id)
}

class UpdateAppParamsUseCase @Inject constructor(private val repository: ConfigProductRepository) {
    suspend operator fun invoke(param: ParamModel) = repository.updateParam(param)
}
