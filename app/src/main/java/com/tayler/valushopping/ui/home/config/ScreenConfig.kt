package com.tayler.valushopping.ui.home.config

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.utils.getDrawableResId
import com.valu.uitaycompose.utils.textSeB18
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ScreenConfig(viewModel: ConfigViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val items by viewModel.itemsState.collectAsStateWithLifecycle()

    var isDelay by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadConfigData(context)
        delay(1000L.milliseconds)
        isDelay = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDelay) Color.White else Color.Transparent)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items) { item ->
                AdminRowItem(
                    admin = item,
                    isDelay = isDelay
                ) { value ->
                    onActionItem(value)
                }
            }
        }
    }
}

private fun onActionItem(value : ItemModel){
    when(value.id){
        2 -> {
        }

        3 -> {
        }

        4 -> {
        }

        5 -> {
        }

        6 -> {
        }
        50 -> {
        }

        else -> {

        }
    }
}

@Composable
fun AdminRowItem(
    admin: ItemModel,
    isDelay: Boolean,
    onClickItem: (ItemModel) -> Unit
) {
    val iconResId = remember(admin.icon) {
        getDrawableResId(admin.icon)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if(isDelay)Color.White else Color.Transparent)
                .clickable { onClickItem(admin) }
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "itemscongif${admin.id}",
                tint = AppDataVale.getColorPrincipal().first
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = admin.title,
                color = AppDataVale.getColorPrincipal().first,
                style = textSeB18,
            )
        }
    }
}