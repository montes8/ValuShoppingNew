package com.tayler.valushopping.ui.splash

import android.R.attr.opacity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tayler.valushopping.R
import com.valu.uitaycompose.swipe.UiTayGif

@Composable
fun ScreenSplash(
    flagStyleText: String,
    bgServiceVisible: Boolean
) {

    var startAnimation by remember { mutableStateOf(false) }
    val startAnimationanim = remember { mutableStateOf(false) }

    val offsetTop by animateDpAsState(
        targetValue = if (startAnimationanim.value) 0.dp else (-150).dp,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "LogoAnimation"
    )


    val opacity by animateFloatAsState(
        targetValue = if (startAnimationanim.value) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "FadeIn"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (
            bgImage, logoOne, logoTwo, logoThree, logoFour,
            centerContent, bagTopLeft, bagTopRight, bagBottomLeft, bagBottomRight
        ) = createRefs()

        // 1. Fondo de Splash
        Image(
            painter = painterResource(id = R.drawable.ic_bg_general),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(bgImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // 2. Logos de las esquinas (Visibilidad controlada por bgService)
        if (bgServiceVisible) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_margin),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .padding(16.dp)
                    .constrainAs(logoOne) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_splash_margin),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .padding(bottom = 16.dp)
                    .constrainAs(logoTwo) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )
            // Aquí puedes cambiar por Lottie si el logoThree y logoFour son animaciones Lottie
            Image(
                painter = painterResource(id = R.drawable.ic_splash_margin),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .padding(top = 16.dp)
                    .offset(x = 20.dp) // Equivale al marginEnd negativo
                    .constrainAs(logoThree) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_splash_margin),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .padding(bottom = 16.dp)
                    .offset(x = 20.dp)
                    .constrainAs(logoFour) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(centerContent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 40.dp, start = 70.dp, end = 70.dp)
        ) {


            UiTayGif(
                resId = R.drawable.gif_splash,
                width = 150.dp,
                height = 150.dp,
                modifier = Modifier
                    .offset(y = offsetTop)
                    .graphicsLayer(alpha = opacity)
            )

            Text(
                text = flagStyleText, // Viene del ViewModel
                fontFamily = FontFamily(Font(com.valu.uitaycompose.R.font.penny_regular)),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.Black, // Reemplaza por color resource "tay_edit_basic_bg_corner_active" si es necesario
                modifier = Modifier.offset(y = (-70).dp) // Acerca el texto al Lottie
            )
        }

        // 4. Bolsas Animadas (Top)
        AnimatedVisibility(
            visible = startAnimation,
            enter = slideInVertically(
                initialOffsetY = { -it - 200 }, // Desliza desde arriba
                animationSpec = tween(1000)
            ),
            modifier = Modifier.constrainAs(bagTopLeft) {
                top.linkTo(centerContent.top)
                start.linkTo(parent.start, margin = 40.dp)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shopping_basket),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }

        AnimatedVisibility(
            visible = startAnimation,
            enter = slideInVertically(
                initialOffsetY = { -it - 200 },
                animationSpec = tween(1000)
            ),
            modifier = Modifier.constrainAs(bagTopRight) {
                top.linkTo(centerContent.top)
                end.linkTo(parent.end, margin = 40.dp)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shopping_basket),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }

        // 5. Bolsas Animadas (Bottom)
        AnimatedVisibility(
            visible = startAnimation,
            enter = slideInVertically(
                initialOffsetY = { it + 200 }, // Desliza desde abajo
                animationSpec = tween(1000)
            ),
            modifier = Modifier.constrainAs(bagBottomLeft) {
                bottom.linkTo(centerContent.bottom, margin = (-60).dp)
                start.linkTo(centerContent.start, margin = (-30).dp)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shopping_basket),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }

        AnimatedVisibility(
            visible = startAnimation,
            enter = slideInVertically(
                initialOffsetY = { it + 200 },
                animationSpec = tween(1000)
            ),
            modifier = Modifier.constrainAs(bagBottomRight) {
                bottom.linkTo(centerContent.bottom, margin = (-60).dp)
                end.linkTo(centerContent.end, margin = (-30).dp)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shopping_basket),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}