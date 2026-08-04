package com.tayler.valushopping.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.tayler.valushopping.R
import com.tayler.valushopping.component.NavigationNavBarHost
import com.tayler.valushopping.component.ScreenInitNav
import com.tayler.valushopping.component.UiTayDrawer
import com.tayler.valushopping.component.mapperNavBar
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.drawerItems
import com.tayler.valushopping.entity.itemsNavBar
import com.tayler.valushopping.utils.setImageLogout
import com.tayler.valushopping.utils.setImageMenu
import com.valu.uitaycompose.extra.UiTayCToolBar
import com.valu.uitaycompose.model.UiTayNavBarModel
import com.valu.uitaycompose.model.UiToolBarModel
import com.valu.uitaycompose.navigation.UiTayBottomBar
import com.valu.uitaycompose.swipe.UiTayUrlImage
import kotlinx.coroutines.launch

@Composable
fun ScreenHome(onNavigateToMain: (ScreenInitNav) -> Unit) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentActionId by remember { mutableIntStateOf(-1) }
    var currentActionIdNavBar by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val colorStyle = AppDataVale.getColorPrincipal()
    val navController = rememberNavController()
    val itemsListNavBar = itemsNavBar.toMutableList()
    val activity = LocalActivity.current as ComponentActivity

    BackHandler(enabled = true) {
        activity.moveTaskToBack(true)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f),
                windowInsets = WindowInsets(0, 0, 0, 0)
            ) {
                UiTayDrawer(
                    items = drawerItems,
                    currentActionId = currentActionId,
                    bgColor = colorStyle.third,
                    text = "Valu",
                    model =
                        UiTayNavBarModel()
                            .uiBgColor(colorStyle.first)
                            .uiColorSelected(colorStyle.first)
                            .uiUnColorSelected(colorStyle.second)
                            .uiTextColorSelected(colorStyle.first)
                            .uiTextUnColorSelected(colorStyle.second)
                ) { menuItem ->
                    currentActionId = menuItem.action
                    scope.launch {
                        drawerState.close()
                    }
                    when (menuItem.action) {
                        5 -> {}
                        0 -> {}
                        1 -> {}
                        2 -> {}
                        3 -> {}
                        4 -> {}
                    }
                }
            }
        },
        gesturesEnabled = true
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            UiTayUrlImage(
                url = AppDataVale.getUrlBg(activity),
                drawable = R.drawable.ic_bg_general,
                modifier = Modifier.fillMaxSize()
            )
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    UiTayBottomBar(
                        itemsListNavBar, currentActionIdNavBar, uiTayModifier =
                            UiTayNavBarModel()
                                .uiBgColor(colorStyle.third)
                                .uiColorSelected(colorStyle.first)
                                .uiUnColorSelected(colorStyle.second)
                                .uiTextColorSelected(colorStyle.first)
                                .uiTextUnColorSelected(colorStyle.second)
                    ) { item ->
                        currentActionIdNavBar = item.action
                        navController.navigate(item.action.mapperNavBar()) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                topBar = {
                    UiTayCToolBar(
                        uiTayText = stringResource(R.string.tb_principal),
                        uiTayModifier = UiToolBarModel()
                            .height(70)
                            .iconStart(setImageMenu())
                            .iconEnd(setImageLogout())
                            .backgroundColor(colorStyle.third)
                            .textColor(colorStyle.first)
                            .bgService(AppDataVale.bgService)
                            .urlBgService(
                                AppDataVale.getUrlBgToolbar(activity)
                            )
                            .showEndIcon(AppDataVale.session)
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
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    NavigationNavBarHost(navController = navController){screenInitNav->
                        onNavigateToMain.invoke(screenInitNav)
                    }
                }
            }
        }
    }
}

