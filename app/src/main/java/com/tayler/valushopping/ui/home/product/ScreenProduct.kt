package com.tayler.valushopping.ui.home.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.entity.ProductModel
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.LocalAppDataVale
import com.tayler.valushopping.utils.mapperHeight
import com.valu.uitaycompose.swipe.UiTayUrlImage
import com.valu.uitaycompose.utils.extension.uiTayDialogZoom
import com.valu.uitaycompose.utils.extension.uiTayOpenUrl
import com.valu.uitaycompose.utils.extension.uiTayShimmer
import com.valu.uitaycompose.utils.tay_grey_400
import com.valu.uitaycompose.utils.textB16
import com.valu.uitaycompose.utils.textGabbi10
import com.valu.uitaycompose.utils.textGabbi12
import com.valu.uitaycompose.utils.textGabbi8
import com.valu.uitaycompose.utils.textM14
import com.valu.uitaycompose.utils.textS12
import com.valu.uitaycompose.utils.textS8
import com.valu.uitaycompose.utils.textSe10
import com.valu.uitaycompose.utils.textSe12
import com.valu.uitaycompose.utils.textSeB14
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProduct(onNavigateToMain: (ProductModel) -> Unit) {
    val appDataVale = LocalAppDataVale.current
    val context = LocalContext.current
    val viewModel: DataViewModel = hiltViewModel()

    val productData by viewModel.successLoadProductClientState.collectAsStateWithLifecycle()
    var isRefreshing by remember { mutableStateOf(value = false) }
    val uiState by viewModel.uiStateBase.collectAsStateWithLifecycle()
    val isShimmer = uiState.shimmer
    var isDelay by remember { mutableStateOf(true) }
    val products = productData.first
    val banners = productData.second
    val empty = productData.third

    LaunchedEffect(Unit) {
        isDelay = true
        viewModel.loadProductClient(country = "PE")
        delay(1000L.milliseconds)
        isDelay = false
    }

    LaunchedEffect(productData) {
        if (products.isNotEmpty() || banners.isNotEmpty()) {
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.loadProductClient(country = "PE")
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        ProductMainContent(
            state = ProductMainUiState(
                empty = empty,
                products = products,
                banners = banners,
                isShimmer = isShimmer,
                isDelay = isDelay,
                appDataVale = appDataVale,
                context = context
            ),
            onNavigateToMain = { product ->
                onNavigateToMain(product)
            }
        )
    }
}

data class ProductMainUiState(
    val empty: Boolean,
    val products: List<ProductModel>,
    val banners: List<ProductModel>,
    val isShimmer: Boolean,
    val isDelay: Boolean,
    val appDataVale: AppDataVale,
    val context: android.content.Context
)

@Composable
private fun ProductMainContent(
    state: ProductMainUiState,
    onNavigateToMain: (ProductModel) -> Unit
) {
    if (state.empty && state.products.isEmpty()) {
        EmptyProductState()
    } else {
        ProductListContent(
            products = state.products,
            banners = state.banners,
            isShimmer = state.isShimmer,
            isDelay = state.isDelay,
            appDataVale = state.appDataVale,
            context = state.context,
            onNavigateToMain = onNavigateToMain
        )
    }
}

@Composable
private fun ProductListContent(
    products: List<ProductModel>,
    banners: List<ProductModel>,
    isShimmer: Boolean,
    isDelay: Boolean,
    appDataVale: AppDataVale,
    context: android.content.Context,
    onNavigateToMain: (ProductModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDelay) Color.White else Color.Transparent)
            .testTag("product_lazy_column"),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        if (banners.isNotEmpty()) {
            item {
                BannerSection(
                    banners = banners,
                    isShimmer = isShimmer,
                    isDelay = isDelay,
                    appDataVale = appDataVale,
                    onClickBanner = { banner ->
                        handleItemClick(banner, context, onNavigateToMain)
                    }
                )
            }
        }

        item {
            HeaderSection(products, isShimmer, isDelay, appDataVale = appDataVale)
        }

        items(products) { product ->
            ProductItem(
                product = product,
                isShimmer = isShimmer,
                isDelay = isDelay,
                appDataVale = appDataVale,
                onClickItem = {
                    handleItemClick(product, context, onNavigateToMain)
                },
                onClickImage = {}
            )
        }
    }
}

private fun handleItemClick(
    model: ProductModel,
    context: android.content.Context,
    onNavigateToMain: (ProductModel) -> Unit
) {
    if (model.click) {
        if (model.linkBanner.isNotEmpty()) {
            context.uiTayOpenUrl(model.linkBanner)
        } else {
            onNavigateToMain.invoke(model)
        }
    }
}

@Composable
fun BannerSection(
    banners: List<ProductModel>, isShimmer: Boolean,
    isDelay: Boolean,
    appDataVale: AppDataVale,
    onClickBanner: (ProductModel) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDelay) Color.White else Color.Transparent)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = lazyListState,
            flingBehavior = snapFlingBehavior,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(banners) { banner ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .uiTayShimmer(isLoading = isShimmer, cornerRadius = 12.dp)
                        .clickable { onClickBanner(banner) }
                ) {
                    UiTayUrlImage(
                        url = banner.urlBanner,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val currentIndex by remember {
                derivedStateOf { lazyListState.firstVisibleItemIndex }
            }

            repeat(banners.size) { iteration ->
                val color = if (currentIndex == iteration) appDataVale.getColorPrincipal().first
                else appDataVale.getColorPrincipal().third
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                        .uiTayShimmer(isLoading = isShimmer, cornerRadius = 4.dp)
                )
            }
        }
    }
}

@Composable
fun HeaderSection(products: List<ProductModel>, isShimmer: Boolean, isDelay: Boolean, appDataVale: AppDataVale) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(if (isDelay) Color.White else Color.Transparent)
            .uiTayShimmer(isLoading = isShimmer, cornerRadius = 24.dp),
    ) {
        Text(
            text = stringResource(R.string.text_choose_product),
            modifier = Modifier.uiTayShimmer(isLoading = isShimmer, cornerRadius = 24.dp),
            color = appDataVale.getColorPrincipal().first,
            style = textB16,
            textAlign = TextAlign.Start
        )
        if (products.size > (appDataVale.paramData.countProduct?.toInt() ?: 30)) {
            Text(
                text = stringResource(R.string.text_more_all),
                color = appDataVale.getColorPrincipal().first,
                style = textB16,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun ProductItem(
    product: ProductModel,
    isShimmer: Boolean, isDelay: Boolean,
    appDataVale: AppDataVale,
    onClickItem: (ProductModel) -> Unit,
    onClickImage: (String) -> Unit
) {
    var showZoom by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(if (isDelay) Color.White else Color.Transparent)
            .uiTayShimmer(isLoading = isShimmer, cornerRadius = 24.dp)
            .clickable { onClickItem(product) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(mapperHeight(product.sizeHeight)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFE0E0E0))
                    .clickable { onClickImage(product.url) }
            ) {
                UiTayUrlImage(
                    url = product.url,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            showZoom = true
                        }
                        .uiTayDialogZoom(
                            showDialog = showZoom,
                            imageUrl = product.url,
                            onShowDialogChange = { newState -> showZoom = newState },
                        ),
                )

                Text(
                    text = stringResource(if(product.stateNew)R.string.text_new else R.string.text_semi_new),
                    style = textS8,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color(0xFF4CAF50))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                        .align(Alignment.TopStart)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = product.name,
                    style = textSeB14,
                    color = appDataVale.getColorPrincipal().first,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "C/U:", style = textGabbi8, color = Color.Black)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = product.getPriceUnit(), style = textS12, color = Color.Black)
                    }

                    if (product.visiblePriceDoc()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "DOC/:", style = textGabbi8, color = Color.Black)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = product.getPriceDoc(), style = textS12, color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.description,
                    style = textGabbi12,
                    color = Color.Gray,
                    maxLines = product.getLinesProduct(),
                    overflow = TextOverflow.Ellipsis
                )

                if (product.visibleDelivery()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = product.deliveryPoint,
                        style = textGabbi10,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = product.getSeller(), style = textSe10, color = Color.DarkGray)

                Text(
                    text = product.getMapperTypeAndGender(appDataVale.categories),
                    style = textSe12,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun EmptyProductState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_info_error),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_empty_list),
            style = textM14,
            color = tay_grey_400,
            textAlign = TextAlign.Center
        )
    }
}