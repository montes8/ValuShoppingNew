package com.tayler.valushopping.ui.home.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tayler.valushopping.component.UiTayDrawer
import com.tayler.valushopping.entity.LocalAppDataVale
import com.tayler.valushopping.entity.drawerItems
import com.valu.uitaycompose.model.UiTayNavBarModel

@Composable
fun HomeDrawer(
    currentActionId: Int,
    onActionClick: (Int) -> Unit
) {
    val appDataVale = LocalAppDataVale.current
    val colorStyle = appDataVale.getColorPrincipal()

    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.8f),
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        UiTayDrawer(
            items = drawerItems,
            currentActionId = currentActionId,
            bgColor = colorStyle.third,
            text = "Valu",
            model = UiTayNavBarModel()
                .uiBgColor(colorStyle.first)
                .uiColorSelected(colorStyle.first)
                .uiUnColorSelected(colorStyle.second)
                .uiTextColorSelected(colorStyle.first)
                .uiTextUnColorSelected(colorStyle.second)
        ) { menuItem ->
            onActionClick(menuItem.action)
        }
    }
}
