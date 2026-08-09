package com.tayler.valushopping.ui.splash

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.entity.exception.UiTayApiException
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.entity.LocalAppDataVale
import com.tayler.valushopping.ui.base.LocalGlobalUiStateManager
import com.tayler.valushopping.utils.validateHourApp
import com.valu.uitaycompose.swipe.UiTayUrlImage
import com.valu.uitaycompose.utils.textPenny25
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun ScreenSplash(onNavigateToMain: () -> Unit) {
    val appDataVale = LocalAppDataVale.current
    val globalUiStateManager = LocalGlobalUiStateManager.current
    val activity = LocalActivity.current as ComponentActivity
    val viewModel: AppViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val paramResponse by viewModel.successParamState.collectAsStateWithLifecycle()
    val globalState by globalUiStateManager.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initSplash()
    }

    LaunchedEffect(globalState.popUpGeneric) {
        if (globalState.popUpGeneric){
            activity.finish()
        }
    }

    LaunchedEffect(paramResponse) {
        paramResponse?.let { param ->
            val iconActual = if (appDataVale.paramData.idIconOld == "0" || appDataVale.paramData.idIconOld.isEmpty()) "Principal" else appDataVale.paramData.idIconOld
            val iconNew = param.idIcon.ifEmpty { "Principal" }

            appDataVale.paramData = param

            if (iconActual != iconNew) {
                val updatedData = param.copy(idIconOld = iconNew)
                viewModel.execute(loading = false) {
                    viewModel.appUseCase.saveParam(updatedData)
                }
            }

            if (appDataVale.paramData.validateHourApp()) {
                delay(2.seconds)
                onNavigateToMain()
            } else {
                globalUiStateManager.updateUiState { currentState ->
                    currentState.copy(
                        error = true, 
                        errorType = UiTayApiException(messageApi = appDataVale.mapperDialogText())
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BackgroundImage( activity, appDataVale)

        CenterContent(
            modifier = Modifier.align(Alignment.Center), state
        )

        if (!state.showLogo) {
            CornerBagsSection()
        }

        SideBasketsSection()
    }
}


@Composable
private fun BackgroundImage(context: Context, appDataVale: AppDataVale) {
    Box(modifier = Modifier.fillMaxSize()) {
            UiTayUrlImage(
                url = appDataVale.getUrlBgSplash(context), drawable = R.drawable.ic_bg_general,
                modifier = Modifier.fillMaxSize()
            )
    }
}

@Composable
private fun CenterContent(modifier: Modifier = Modifier, splashState: SplashUiState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(horizontal = 30.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_bag),
            modifier = Modifier
                .size(200.dp)
                .testTag("splash_bag_image"),
            contentDescription = "Logo de la bolsa"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = splashState.welcomeText,
            color = splashState.textColor,
            style = textPenny25,
            textAlign = TextAlign.Center,
            modifier = Modifier.offset(y = (-70).dp)
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
                .size(60.dp)
                .constrainAs(basketTopLeft) {
                    top.linkTo(parent.top, margin = 180.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .constrainAs(basketTopRight) {
                    top.linkTo(parent.top, margin = 180.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .constrainAs(basketBottomLeft) {
                    bottom.linkTo(parent.bottom, margin = 180.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_shopping_basket),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .constrainAs(basketBottomRight) {
                    bottom.linkTo(parent.bottom, margin = 180.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                }
        )
    }
}

