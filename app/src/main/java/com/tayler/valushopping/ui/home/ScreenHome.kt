package com.tayler.valushopping.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.tayler.valushopping.component.drawer.MyDrawer
import com.tayler.valushopping.component.drawer.drawerItems

@Composable
fun ScreenHome() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentActionId by remember { mutableIntStateOf(5) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f)
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
            floatingActionButton = {},
            floatingActionButtonPosition = FabPosition.End,
            topBar = {}
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {

            }
        }
    }
}

