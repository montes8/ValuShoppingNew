package com.tayler.home.entity

import com.tayler.home.R
import com.tayler.ui.R as UiR
import com.valu.uitaycompose.model.UiTayNavBarItem

val itemsNavBar = listOf(
    UiTayNavBarItem(
        titleId = R.string.text_item_init,
        iconId = UiR.drawable.ic_home,
        action = 0
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_product,
        iconId = UiR.drawable.ic_clothes,
        action = 1
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_categories,
        iconId = UiR.drawable.ic_category,
        action = 2
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_config,
        iconId = UiR.drawable.ic_admin,
        action = 3
    )
)

val drawerItems = listOf(
    UiTayNavBarItem(
        titleId = R.string.text_item_profile,
        iconId = UiR.drawable.ic_profile,
        action = 0
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_about,
        iconId = UiR.drawable.ic_home,
        action = 1
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_part,
        iconId = UiR.drawable.ic_about,
        action = 2
    ),

    UiTayNavBarItem(
        titleId = R.string.text_item_delivery,
        iconId = UiR.drawable.ic_map,
        action = 3
    ),
    UiTayNavBarItem(
        titleId = R.string.text_item_support,
        iconId = UiR.drawable.ic_support,
        action = 4
    ), UiTayNavBarItem(
        titleId = R.string.text_item_social,
        iconId = UiR.drawable.ic_facebook_pink,
        action = 5
    )

)
