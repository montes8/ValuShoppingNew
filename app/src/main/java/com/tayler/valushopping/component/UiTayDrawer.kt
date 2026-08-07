package com.tayler.valushopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tayler.valushopping.R
import com.valu.uitaycompose.model.UiTayNavBarItem
import com.valu.uitaycompose.model.UiTayNavBarModel
import kotlin.collections.forEach

@Composable
fun UiTayDrawer(
    items: List<UiTayNavBarItem>,
    currentActionId: Int,
    bgColor : Color,
    text : String,
    model : UiTayNavBarModel = UiTayNavBarModel(),
    onItemClick: (UiTayNavBarItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bg_menu),
                contentDescription = "Bg Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(bgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "ProfileAvatar",
                        modifier = Modifier.size(35.dp),
                        tint = model.uiColorSelected
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = model.uiColorSelected,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { item ->
            val isSelected = item.action == currentActionId
            UiTayDrawerItem(item, isSelected,model,bgColor) {
                onItemClick(item)
            }
        }
    }
}

@Composable
fun UiTayDrawerItem(
    item: UiTayNavBarItem,
    selected: Boolean,
    model : UiTayNavBarModel,
    bgColor : Color,
    onItemClick: (UiTayNavBarItem) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) bgColor else Color.Transparent)
            .clickable { onItemClick(item) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = if (selected) model.uiColorSelected else model.uiUnColorSelected
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.title,
            style = TextStyle(fontSize = 16.sp),
            color = if (selected) model.uiTextColorSelected else model.uiTextUnColorSelected
        )
    }
}
