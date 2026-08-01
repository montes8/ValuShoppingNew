package com.tayler.valushopping.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tayler.valushopping.R
import com.valu.uitaycompose.swipe.UiTayGif

@Composable
fun ScreenSplash() {
    val showCornerBags = true

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BackgroundImage()

        CenterContent(
            modifier = Modifier.align(Alignment.Center)
        )

        if (showCornerBags) {
            CornerBagsSection()
        }

        SideBasketsSection()
    }
}


@Composable
private fun BackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_bg_general),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CenterContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(horizontal = 30.dp)
    ) {
        UiTayGif(
            resId = R.drawable.gif_splash,
            width = 250.dp,
            height = 250.dp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "VALU \nSHOOPING",
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(com.valu.uitaycompose.R.font.penny_regular)),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CornerBagsSection() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (bagTopLeft, bagTopRight, bagBottomLeft, bagBottomRight) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_splash_margin),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .constrainAs(bagTopLeft) {
                    top.linkTo(parent.top, margin = 12.dp)
                    start.linkTo(parent.start, margin = 12.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_splash_margin),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .constrainAs(bagTopRight) {
                    top.linkTo(parent.top, margin = 12.dp)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_splash_margin),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .constrainAs(bagBottomLeft) {
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = 12.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_splash_margin),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .constrainAs(bagBottomRight) {
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun SideBasketsSection() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (basketTopLeft, basketTopRight, basketBottomLeft, basketBottomRight) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(basketTopLeft) {
                    top.linkTo(parent.top, margin = 180.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(basketTopRight) {
                    top.linkTo(parent.top, margin = 180.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(basketBottomLeft) {
                    bottom.linkTo(parent.bottom, margin = 180.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(basketBottomRight) {
                    bottom.linkTo(parent.bottom, margin = 180.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                }
        )
    }
}