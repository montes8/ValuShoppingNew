package com.tayler.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.model.UiToolBarModel

@Composable
fun ValuScreen(
    title: String = stringResource(R.string.tb_principal),
    onBackClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val appDataVale = LocalAppDataVale.current
    val colorStyle = appDataVale.getColorPrincipal()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            UiTayCToolBar(
                uiTayText = title,
                uiTayModifier = UiToolBarModel()
                    .backgroundColor(colorStyle.third)
                    .textColor(colorStyle.first)
                    .bgService(appDataVale.paramData.bgService)
                    .urlBgService(appDataVale.getUrlBgToolbar(context))
                    .iconColor(colorStyle.first)
            ) { _ ->
                onBackClick?.invoke()
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
