package com.tayler.valushopping.ui.home.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.LocalAppDataVale
import com.tayler.valushopping.utils.setImageLogout
import com.tayler.valushopping.utils.setImageMenu
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.model.UiToolBarModel

@Composable
fun HomeTopBar(onOpenDrawer: () -> Unit) {
    val appDataVale = LocalAppDataVale.current
    val colorStyle = appDataVale.getColorPrincipal()
    val activity = LocalActivity.current as ComponentActivity

    UiTayCToolBar(
        uiTayText = stringResource(R.string.tb_principal),
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
