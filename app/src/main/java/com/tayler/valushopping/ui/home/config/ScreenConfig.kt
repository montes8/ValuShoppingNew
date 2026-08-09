package com.tayler.valushopping.ui.home.config

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.LocalAppDataVale
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.utils.getDrawableResId
import com.tayler.valushopping.utils.openWhatsApp
import com.valu.uitaycompose.model.UiTayDialogModel
import com.valu.uitaycompose.model.UiTayDialogModelCustom
import com.valu.uitaycompose.utils.COUNTRY_CODE_PE
import com.valu.uitaycompose.utils.extension.uiTayShowDialog
import com.valu.uitaycompose.utils.tay_green_400
import com.valu.uitaycompose.utils.textSeB18
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ScreenConfig(onNavigateToMain: () -> Unit) {
    val appDataVale = LocalAppDataVale.current
    val context = LocalContext.current

    val viewModel: ConfigViewModel = hiltViewModel()
    val items by viewModel.itemsState.collectAsStateWithLifecycle()

    var textRes by remember { mutableIntStateOf(R.string.text_support) }
    var titleModal by remember { mutableIntStateOf(R.string.text_title_support) }
    var subTitleModal by remember { mutableIntStateOf(R.string.sub_text_title_support) }
    val message = stringResource(textRes)

    var isDelay by remember { mutableStateOf(true) }
    var showModal by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isDelay = true
        viewModel.loadConfigData(context)
        delay(1000L.milliseconds)
        isDelay = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDelay) Color.White else Color.Transparent)
            .uiTayShowDialog(
                showDialog = showModal,
                model = UiTayDialogModel(
                    image = R.drawable.ic_support_whatsapp,
                    title = stringResource(titleModal) ,
                    subTitle = stringResource(subTitleModal),
                    styleCustom =
                        UiTayDialogModelCustom(
                            btnAcceptSolidColor = tay_green_400,
                            btnAcceptStrokeColor = tay_green_400
                        )
                )
            ){ dialogResult ->
                showModal = false
                if (dialogResult){
                    context.openWhatsApp(appDataVale.paramData.phone, message,COUNTRY_CODE_PE)

                }
            }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("config_lazy_column"),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items) { item ->
                AdminRowItem(
                    admin = item,
                    isDelay = isDelay,
                    appDataVale = appDataVale
                ) { value ->
                    when(value.id){
                        4 -> {
                            onNavigateToMain.invoke()
                        }

                        5 -> {
                            textRes = R.string.text_support
                            titleModal =  R.string.text_title_support
                            subTitleModal = R.string.sub_text_title_support
                            showModal = true
                        }

                        7 -> {
                            textRes = R.string.text_body_join
                            titleModal =  R.string.text_title_join
                            subTitleModal = R.string.sub_text_title_join
                            showModal = true
                        }
                        else -> {
                            onNavigateToMain.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminRowItem(
    admin: ItemModel,
    isDelay: Boolean,
    appDataVale: AppDataVale,
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
                contentDescription = "itemsconfig${admin.id}",
                tint = appDataVale.getColorPrincipal().first
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = admin.title,
                color = appDataVale.getColorPrincipal().first,
                style = textSeB18,
            )
        }
    }
}