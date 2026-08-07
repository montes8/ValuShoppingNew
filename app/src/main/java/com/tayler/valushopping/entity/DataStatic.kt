package com.tayler.valushopping.entity

import com.tayler.valushopping.R
import com.valu.uitaycompose.model.UiTayNavBarItem

val itemsNavBar = listOf(
    UiTayNavBarItem(
        titleId = R.string.text_item_init,
        iconId = R.drawable.ic_home,
        action = 0
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_product,
        iconId = R.drawable.ic_clothes,
        action = 1
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_categories,
        iconId = R.drawable.ic_category,
        action = 2
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_config,
        iconId = R.drawable.ic_admin,
        action = 3
    )
)

val drawerItems = listOf(
    UiTayNavBarItem(
        titleId = R.string.text_item_profile,
        iconId = R.drawable.ic_profile,
        action = 0
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_about,
        iconId = R.drawable.ic_home,
        action = 1
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_part,
        iconId = R.drawable.ic_about,
        action = 2
    ),

    UiTayNavBarItem(
        titleId = R.string.text_item_delivery,
        iconId = R.drawable.ic_map,
        action = 3
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_support,
        iconId = R.drawable.ic_support,
        action = 4
    ), UiTayNavBarItem(
        titleId = R.string.text_item_social,
        iconId = R.drawable.ic_facebook_pink,
        action = 5
    )

)
