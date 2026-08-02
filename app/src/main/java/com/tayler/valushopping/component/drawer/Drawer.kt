package com.tayler.valushopping.component.drawer

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
import androidx.compose.material3.DrawerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tayler.valushopping.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerMenuItem(
    val title: String,
    val icon: Int,
    val action: Int
)

val drawerItems = listOf(
    DrawerMenuItem(
        title = "Perfil",
        icon = R.drawable.ic_profile,
        action = 5
    ),
    DrawerMenuItem(
        title = "Sobre nostros",
        icon = R.drawable.ic_home,
        action = 0
    ),
    DrawerMenuItem(
        title = "Se parte de nosotros",
        icon = R.drawable.ic_about,
        action = 1
    ),
    DrawerMenuItem(
        title = "Puntos de entrega",
        icon = R.drawable.ic_map,
        action = 2
    ),
    DrawerMenuItem(
        title = "soporte tecnico",
        icon = R.drawable.ic_support,
        action = 3
    ), DrawerMenuItem(
        title = "Facebook",
        icon = R.drawable.ic_facebook_pink,
        action = 4
    )

)


@Composable
fun MyDrawer(
    scope: CoroutineScope,
    scaffoldState: DrawerState,
    items: List<DrawerMenuItem>,
    currentActionId: Int,
    onItemClick: (DrawerMenuItem) -> Unit
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
                        .background(Color(0xFFFF80AB)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "ProfileAvatar",
                        modifier = Modifier.size(35.dp),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Valu",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE91E63)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { item ->
            val isSelected = item.action == currentActionId
            DrawerItem(item = item, selected = isSelected) {
                scope.launch {
                    scaffoldState.close()
                }
                onItemClick(item)
            }
        }
    }
}

@Composable
fun DrawerItem(
    item: DrawerMenuItem,
    selected: Boolean,
    onItemClick: (DrawerMenuItem) -> Unit
) {
    val pinkBackground = Color(0xFFFFEBEE)
    val pinkDark = Color(0xFFE91E63)
    val grayColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) pinkBackground else Color.Transparent)
            .clickable { onItemClick(item) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = if (selected) pinkDark else grayColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.title,
            style = TextStyle(fontSize = 16.sp),
            color = if (selected) pinkDark else grayColor
        )
    }
}




