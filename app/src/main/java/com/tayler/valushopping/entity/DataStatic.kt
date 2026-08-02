package com.tayler.valushopping.entity

import com.tayler.valushopping.R
import com.valu.uitaycompose.model.UiTayNavBarItem

val itemsNavBar = listOf(
    UiTayNavBarItem(
        title = "Inicio",
        icon = R.drawable.ic_home,
        action = 0
    ),
    UiTayNavBarItem(
        title = "Productos",
        icon = R.drawable.ic_clothes,
        action = 1
    ),
    UiTayNavBarItem(
        title = "Categorias",
        icon = R.drawable.ic_category,
        action = 2
    ),
    UiTayNavBarItem(
        title = "Config",
        icon = R.drawable.ic_admin,
        action = 3
    )
)

val drawerItems = listOf(
    UiTayNavBarItem(
        title = "Perfil",
        icon = R.drawable.ic_profile,
        action = 5
    ),
    UiTayNavBarItem(
        title = "Sobre nostros",
        icon = R.drawable.ic_home,
        action = 0
    ),
    UiTayNavBarItem(
        title = "Se parte de nosotros",
        icon = R.drawable.ic_about,
        action = 1
    ),

    UiTayNavBarItem(
        title = "Puntos de entrega",
        icon = R.drawable.ic_map,
        action = 2
    ),
    UiTayNavBarItem(
        title = "soporte tecnico",
        icon = R.drawable.ic_support,
        action = 3
    ), UiTayNavBarItem(
        title = "Facebook",
        icon = R.drawable.ic_facebook_pink,
        action = 4
    )

)
