package com.tayler.valushopping.ui.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tayler.valushopping.entity.AppDataVale
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.model.UiToolBarModel
import com.valu.uitaycompose.utils.textSeB14
import com.tayler.valushopping.R
import com.valu.uitaycompose.utils.tay_grey_400

@Composable
fun ScreenAbout(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val colorStyle = AppDataVale.getColorPrincipal()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UiTayCToolBar(
            uiTayText = stringResource(R.string.tb_principal),
            uiTayModifier = UiToolBarModel()
                .backgroundColor(colorStyle.third)
                .textColor(colorStyle.first)
                .bgService(AppDataVale.bgService)
                .urlBgService(
                    AppDataVale.getUrlBgToolbar(context)
                ).iconColor(colorStyle.first)
        ) { _ ->
            onNavigateBack.invoke()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            AboutItem(
                title = stringResource(R.string.title_parte_mode),
                description = stringResource(id = R.string.text_about_use),
                color = colorStyle.first
            )

            AboutItem(
                title = stringResource(R.string.title_parte_blocking),
                description = stringResource(id = R.string.text_about_blocking),
                color = colorStyle.first
            )

            AboutItem(
                title = stringResource(R.string.title_parte_recommendation),
                description = stringResource(id = R.string.text_about_recommended),
                 color = colorStyle.first
            )

            AboutItem(
                title =stringResource(R.string.title_parte_profile),
                description = stringResource(id = R.string.text_about_profile),
                color = colorStyle.first,
                showBottomSpacer = false
            )
        }
    }
}

@Composable
fun AboutItem(
    title: String,
    description: String,
    color: Color,
    showBottomSpacer: Boolean = true
) {
    Text(
        text = title,
        style = textSeB14,
        color = color
    )

    Text(
        text = description,
        fontFamily = FontFamily.Serif,
        fontSize = 10.sp,
        color = tay_grey_400
    )

    if (showBottomSpacer) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}