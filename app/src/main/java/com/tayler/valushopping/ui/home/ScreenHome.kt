package com.tayler.valushopping.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tayler.valushopping.R
import com.tayler.valushopping.component.drawer.MyDrawer
import com.tayler.valushopping.component.drawer.drawerItems
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.utils.setImageLogout
import com.tayler.valushopping.utils.setImageMenu
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.model.UiToolBarModel
import kotlinx.coroutines.launch

@Composable
fun ScreenHome() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentActionId by remember { mutableIntStateOf(5) }
    val scope = rememberCoroutineScope()
    val colorStyle = AppDataVale.getColorPrincipal()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f),
                windowInsets = WindowInsets(0, 0, 0, 0)
            ) {
                MyDrawer(
                    scope = scope,
                    scaffoldState = drawerState,
                    items = drawerItems,
                    currentActionId = currentActionId
                ) { menuItem ->
                    currentActionId = menuItem.action

                    when (menuItem.action) {
                        5 -> {

                        }
                        0 -> {

                        }
                        1 -> {

                        }
                        2 -> {

                        }
                        3 -> {

                        }
                        4 -> {

                        }
                    }
                }
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                UiTayCToolBar(
                    uiTayText = stringResource(R.string.tb_principal),
                    uiTayModifier = UiToolBarModel()
                        .height(70)
                        .iconStart(setImageMenu())
                        .iconEnd(setImageLogout())
                        .backgroundColor(colorStyle.third)
                        .textColor(colorStyle.first)
                        .bgService(false)
                        .urlBgService("")
                        .useOriginalTint(true)
                ) { isStartIcon ->
                    if (isStartIcon) {
                        scope.launch {
                            drawerState.open()
                        }
                    } else {
                        // Acción del botón de menú
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {

            }
        }
    }
}

