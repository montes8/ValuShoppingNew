package com.tayler.valushopping.ui.home.config

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.utils.JSON_ITEM
import com.tayler.valushopping.utils.JSON_ITEM_ADMIN
import com.valu.uitaycompose.utils.extension.uiTayDataJson
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ConfigViewModel @Inject constructor() : ViewModel() {

    private val _itemsState = MutableStateFlow<List<ItemModel>>(emptyList())
    val itemsState: StateFlow<List<ItemModel>> = _itemsState.asStateFlow()

    fun loadConfigData(context: Context) {
        if (_itemsState.value.isNotEmpty()) return
        val jsonFile = if (AppDataVale.paramData.session) JSON_ITEM_ADMIN else JSON_ITEM
        val loadedItems: ArrayList<ItemModel> = uiTayDataJson(context, jsonFile)
        _itemsState.value = loadedItems.toList()
    }
}