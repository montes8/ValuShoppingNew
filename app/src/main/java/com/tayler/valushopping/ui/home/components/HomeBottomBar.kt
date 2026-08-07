package com.tayler.valushopping.ui.home.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.tayler.valushopping.component.mapperNavBar
import com.tayler.valushopping.entity.LocalAppDataVale
import com.tayler.valushopping.entity.itemsNavBar
import com.valu.uitaycompose.model.UiTayNavBarModel
import com.valu.uitaycompose.navigation.UiTayBottomBar

@Composable
fun HomeBottomBar(
    navController: NavHostController,
    currentActionIdNavBar: Int,
    onActionChange: (Int) -> Unit
) {
    val appDataVale = LocalAppDataVale.current
    val colorStyle = appDataVale.getColorPrincipal()
    val itemsListNavBar = itemsNavBar.toMutableList()

    UiTayBottomBar(
        itemsListNavBar,
        currentActionIdNavBar,
        uiTayModifier = UiTayNavBarModel()
            .uiBgColor(colorStyle.third)
            .uiColorSelected(colorStyle.first)
            .uiUnColorSelected(colorStyle.second)
            .uiTextColorSelected(colorStyle.first)
            .uiTextUnColorSelected(colorStyle.second)
    ) { item ->
        onActionChange(item.action)
        navController.navigate(item.action.mapperNavBar()) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
