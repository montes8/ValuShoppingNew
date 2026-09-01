package com.tayler.configproduct.ui.product_list

import com.tayler.core.model.ProductModel
import com.tayler.configproduct.domain.usecase.SaveProductUseCase
import com.tayler.configproduct.domain.usecase.UpdateProductUseCase
import com.tayler.configproduct.domain.usecase.DeleteProductUseCase
import com.tayler.configproduct.domain.usecase.UploadImageUseCase
import com.tayler.ui.di.IoDispatcher
import com.tayler.ui.ui.base.BaseViewModel
import com.tayler.ui.ui.base.GlobalUiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageProductViewModel @Inject constructor(
    private val saveProductUseCase: SaveProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    fun saveProduct(product: ProductModel, imageFile: File?) {
        execute(globalUiStateManager = globalUiStateManager) {
            val savedProduct = io { saveProductUseCase(product) }
            if (imageFile != null) {
                io { uploadImageUseCase.uploadPrincipal(imageFile, savedProduct.uid) }
            }
        }
    }

    fun updateProduct(product: ProductModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            io { updateProductUseCase(product) }
        }
    }

    fun deleteProduct(id: String) {
        execute(globalUiStateManager = globalUiStateManager) {
            io { deleteProductUseCase(id) }
        }
    }
}
