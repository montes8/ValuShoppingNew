package com.tayler.valushopping.ui.home.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.entity.CategoryModel
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.LocalAppDataVale
import com.valu.uitaycompose.swipe.UiTayUrlImage
import com.valu.uitaycompose.utils.extension.uiTayShimmer
import com.valu.uitaycompose.utils.tay_grey_100
import com.valu.uitaycompose.utils.textB16
import com.valu.uitaycompose.utils.textB18
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ScreenCategory() {
    val appDataVale = LocalAppDataVale.current
    val viewModel: CategoryViewModel = hiltViewModel()

    val categories by viewModel.successCategoriesState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiStateBase.collectAsStateWithLifecycle()
    val isLoading = uiState.shimmer

    var isDelay by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isDelay = true
        viewModel.loadCategories()
        delay(1000L.milliseconds)
        isDelay = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if(isDelay)Color.White else Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.text_see_more_product),
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    .uiTayShimmer(isLoading = isLoading, cornerRadius = 12.dp),
                style = textB18,
                color = appDataVale.getColorPrincipal().first,
                lineHeight = 24.sp
            )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(categories) { category ->
                        CategoryRowItem(
                            category = category,
                            isLoading = isLoading,isDelay,
                            appDataVale = appDataVale,
                            onClick = {
                                // ListProductCategoryActivity.newInstance(context, category)
                            }
                        )
                    }
                }

        }
    }
}

@Composable
fun CategoryRowItem(
    category: CategoryModel,
    isLoading: Boolean = false,
    isDelay: Boolean,
    appDataVale: AppDataVale,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(if(isDelay)Color.White else Color.Transparent)
            .clickable(enabled = !isLoading) { onClick() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .uiTayShimmer(isLoading = isLoading, cornerRadius = 24.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isLoading) 0.dp else 12.dp),
            colors = CardDefaults.cardColors(containerColor = if(isLoading)Color.White else Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(152.dp)
                    .background(if (isLoading) Color.Transparent else tay_grey_100)
            ) {
                if (!isLoading) {
                    UiTayUrlImage(
                        url = category.url,
                        drawable = R.drawable.ic_bg_general,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .uiTayShimmer(isLoading = isLoading, cornerRadius = 4.dp),
            style = textB16,
            color = appDataVale.getColorPrincipal().first,
            textAlign = TextAlign.Center
        )
    }
}

