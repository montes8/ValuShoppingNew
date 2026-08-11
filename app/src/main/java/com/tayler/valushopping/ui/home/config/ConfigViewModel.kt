package com.tayler.valushopping.ui.home.config

import android.content.Context
import com.tayler.valushopping.di.DefaultDispatcher
import com.tayler.valushopping.di.IoDispatcher
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.utils.JSON_ITEM
import com.tayler.valushopping.utils.JSON_ITEM_ADMIN
import com.valu.uitaycompose.utils.extension.uiTayDataJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
open class ConfigViewModel @Inject constructor(
    private val appDataVale: AppDataVale,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : BaseViewModel(ioDispatcher, defaultDispatcher) {

    private val _itemsState = MutableStateFlow<List<ItemModel>>(emptyList())
    val itemsState: StateFlow<List<ItemModel>> = _itemsState.asStateFlow()

    fun loadConfigData(context: Context) {
        if (_itemsState.value.isNotEmpty()) return
        val jsonFile = if (appDataVale.paramData.session) JSON_ITEM_ADMIN else JSON_ITEM

        execute(loading = false) {
            try {
                val loadedItems = default { loadJsonData(context, jsonFile) }
                _itemsState.value = loadedItems
            } catch (e: Exception) {
                e.printStackTrace()
                _itemsState.value = emptyList()
            }
        }
    }

    /**
     * Método auxiliar para permitir el testeo unitario.
     * Las funciones 'inline' no se pueden mockear directamente.
     */
    open fun loadJsonData(context: Context, fileName: String): List<ItemModel> {
        return uiTayDataJson(context, fileName)
    }
}