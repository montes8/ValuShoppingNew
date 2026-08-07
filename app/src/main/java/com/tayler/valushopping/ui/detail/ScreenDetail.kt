package com.tayler.valushopping.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.entity.ProductModel
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.ui.splash.AppViewModel
import com.tayler.valushopping.utils.mapperCodeSocial
import com.tayler.valushopping.utils.mapperHeight
import com.tayler.valushopping.utils.mapperNextProduct
import com.tayler.valushopping.utils.openWhatsApp
import com.tayler.valushopping.utils.sharedImageViewFromBitmap
import com.valu.uitaycompose.button.UiTayButton
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.modal.UiTayDialogZoomDetail
import com.valu.uitaycompose.model.UTStyleCButton
import com.valu.uitaycompose.model.UTStyleIcon
import com.valu.uitaycompose.model.UiTayButtonModel
import com.valu.uitaycompose.model.UiToolBarModel
import com.valu.uitaycompose.swipe.UiTayUrlImage
import com.valu.uitaycompose.utils.TYPE_CONSULT
import com.valu.uitaycompose.utils.extension.uiTayShowToast
import com.valu.uitaycompose.utils.tay_green_600
import com.valu.uitaycompose.utils.tay_grey_400
import com.valu.uitaycompose.utils.tay_grey_600
import com.valu.uitaycompose.utils.tay_light_blue_500
import com.valu.uitaycompose.utils.tay_red_600
import com.valu.uitaycompose.utils.textB12
import com.valu.uitaycompose.utils.textFlexoB20
import com.valu.uitaycompose.utils.textGabbi10
import com.valu.uitaycompose.utils.textGabbi12
import com.valu.uitaycompose.utils.textSe10
import com.valu.uitaycompose.utils.textSe12
import com.valu.uitaycompose.utils.textSe14
import com.valu.uitaycompose.utils.textSe16
import com.valu.uitaycompose.utils.textSeB18
import kotlinx.coroutines.launch

@Composable
fun ScreenDetail(
    product: ProductModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: DetailViewModel = hiltViewModel()
    val aViewModel: AppViewModel = hiltViewModel()
    val colorStyle = AppDataVale.getColorPrincipal()
    val imagesState by viewModel.successProductImageState.collectAsStateWithLifecycle()

    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(product.uid) {
        viewModel.loadMoreImageProduct(product.uid)
    }

    var showZoomDialog by remember { mutableStateOf<String?>(null) }

    showZoomDialog?.let { imageUrl ->
        UiTayDialogZoomDetail(imageUrl = imageUrl,enableAdvancedZoom = false) {
            showZoomDialog = null
        }
    }

    Scaffold(
        topBar = {
            UiTayCToolBar(
                uiTayText = stringResource(R.string.tb_principal),
                uiTayModifier = UiToolBarModel()
                    .backgroundColor(colorStyle.third)
                    .textColor(colorStyle.first)
                    .bgService(AppDataVale.paramData.bgService)
                    .urlBgService(
                        AppDataVale.getUrlBgToolbar(context)
                    ).iconColor(colorStyle.first)
            ) { _ ->
                onBackClick.invoke()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawWithContent {
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                            drawLayer(graphicsLayer)
                        }
                ) {
                    HeaderProductSection(
                        product = product,
                        colorStyle = colorStyle,
                        onImageClick = { url -> showZoomDialog = url }
                    )
                }

                if (imagesState.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(imagesState) { imageUrl ->
                            MoreImageItem(imageUrl = imageUrl.url) { clickedUrl ->
                                showZoomDialog = clickedUrl
                            }
                        }
                    }
                }

                DescriptionSection(product = product,colorStyle.first)

                DeliverySection(product = product)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                UiTayButton(uiTayText= stringResource(R.string.btn_consult),
                    uiTayStyleBtn  = UTStyleCButton.UI_TAY_SECONDARY,
                    uiTayStyleIcon = UTStyleIcon.START,
                    uiTayBtnModifier = UiTayButtonModel()
                        .textColorSecondary(colorStyle.first)
                        .strokeSecondaryColor(colorStyle.first)
                        .strokeSecondarySelectedColor(colorStyle.first)
                        .iconStart(R.drawable.ic_social)
                ){
                    if (product.state) {
                        aViewModel.saveHistory(TYPE_CONSULT)
                        context.openWhatsApp(
                            phone= product.phone,
                            text=product.mapperNextProduct(),
                            code = product.countryCode.mapperCodeSocial()
                        )
                    } else {
                        context.uiTayShowToast(R.string.toast_not_product)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                UiTayButton(uiTayText= stringResource(R.string.btn_shared),
                    uiTayStyleIcon = UTStyleIcon.START,
                    uiTayBtnModifier = UiTayButtonModel()
                        .bgColor(colorStyle.first)
                        .bgSelectedColor(colorStyle.first)
                        .strokeColor(colorStyle.first)
                        .strokeSelectedColor(colorStyle.first)
                        .iconStart(R.drawable.ic_shared)

                ){
                    if (product.state) {
                        coroutineScope.launch {
                            try {
                                val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                                context.sharedImageViewFromBitmap(bitmap)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                context.uiTayShowToast(R.string.error_image)
                            }
                        }
                    } else {
                        context.uiTayShowToast(R.string.toast_not_product)                   }
                }
            }
        }
    }
}

@Composable
fun HeaderProductSection(
    product: ProductModel,
    colorStyle: Triple<Color, Color, Color>,
    onImageClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier
                .width(152.dp)
                .height(mapperHeight(product.sizeHeight))
                .clickable { onImageClick(product.url) },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, colorStyle.first)
        ) {
            UiTayUrlImage(
                url = product.url,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = product.name ,
                style = textFlexoB20,
                color= colorStyle.first,
                maxLines = 2
            )

            Text(
                text = product.getMapperTypeAndGender(AppDataVale.categories),
                style = textSe14,
                 color = tay_grey_400
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = product.getPriceUnitTwo(),
                    style = textGabbi12,
                        color = tay_grey_600,
                    maxLines = 1
                )
                if (!product.visiblePriceDocView()) {
                    Text(
                        text = product.getPriceDocTwo(),
                        style = textGabbi12,
                        color = tay_grey_600,
                        maxLines = 1
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorStyle.third,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(6.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    painter = painterResource(id = com.valu.uitaycompose.R.drawable.ui_tay_ic_info),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.error_consult_v),
                    style = textGabbi10,
                    color = tay_red_600
                )
            }
        }
    }
}

@Composable
fun MoreImageItem(
    imageUrl: String,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .size(70.dp)
            .clickable { onClick(imageUrl) },
        shape = RoundedCornerShape(8.dp)
    ) {
        UiTayUrlImage(
            url = imageUrl,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun DescriptionSection(product: ProductModel,colorStyle: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.text_description_product),
                style = textSeB18,
                color = colorStyle
            )
            Text(
                text = stringResource(if(product.stateNew)R.string.text_new else R.string.text_semi_new),
                style= textB12,
                color = Color.White,
                modifier = Modifier
                    .background(tay_green_600, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.description ,
            style = textSe16,
            color = tay_grey_400
        )
    }
}

@Composable
fun DeliverySection(product: ProductModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.text_delivery_location),
                    style = textSe14,
                    color = tay_light_blue_500
                )
                Text(
                    text = product.deliveryPoint ,
                    style = textSe12, color = tay_grey_400,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.text_city),
                    style = textSe14, color = tay_light_blue_500
                )
                Text(
                    text = product.district,
                    style = textSe12, color = tay_grey_400,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.text_delivery_location_other),
                style = textSe12,
                color = Color.Black,
            )
            Text(
                text = stringResource(R.string.text_delivery_location_other_value),
                style = textSe10, color = tay_grey_400,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


