package com.tayler.valushopping.ui.option

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.button.UiTayButton
import com.valu.uitaycompose.model.UTStyleCButton

@Composable
fun ScreenOption(onNavigateToMain: (Boolean) -> Unit) {

    val activity = LocalActivity.current as ComponentActivity
    Column(
    modifier = Modifier
    .fillMaxSize()
    .padding(24.dp).background(Color.White),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Elige tipo de usuario",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        UiTayButton(uiTayText= "Si, eres vendedor"){
            onNavigateToMain.invoke(true)

        }

        Spacer(modifier = Modifier.height(16.dp))

        UiTayButton(uiTayText= "Si, eres cliente",
            uiTayStyleBtn = UTStyleCButton.UI_TAY_SECONDARY){
            onNavigateToMain.invoke(false)
        }
    }
}