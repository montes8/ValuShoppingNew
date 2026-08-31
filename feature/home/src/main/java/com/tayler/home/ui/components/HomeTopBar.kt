package com.tayler.home.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.tayler.ui.R as UiR
import com.tayler.ui.LocalAppDataVale
import com.tayler.ui.setImageLogout
import com.tayler.ui.setImageMenu
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.model.UiToolBarModel

@Composable
fun HomeTopBar(onOpenDrawer: () -> Unit) {
    val appDataVale = LocalAppDataVale.current
    val colorStyle = appDataVale.getColorPrincipal()
    val activity = LocalContext.current as ComponentActivity

    Box(modifier = Modifier.testTag("home_top_bar")) {
        UiTayCToolBar(
            uiTayText = stringResource(UiR.string.tb_principal),
            uiTayModifier = UiToolBarModel()
                .height(70)
                .iconStart(setImageMenu(appDataVale))
                .iconEnd(setImageLogout(appDataVale))
                .backgroundColor(colorStyle.third)
                .textColor(colorStyle.first)
                .bgService(appDataVale.paramData.bgService)
                .urlBgService(
                    appDataVale.getUrlBgToolbar(activity)
                )
                .showEndIcon(appDataVale.paramData.session)
                .useOriginalTint(true)
        ) {
            onOpenDrawer()
        }
    }
}
