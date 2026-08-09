package com.tayler.valushopping.ui.home.init

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tayler.entity.ParamModel
import com.tayler.valushopping.R
import com.tayler.valushopping.component.VideoPlayerCompose
import com.tayler.valushopping.entity.LocalAppDataVale
import com.valu.uitaycompose.utils.extension.uiTayOpenUrl
import com.valu.uitaycompose.utils.extension.uiTayUrlFacebook
import com.valu.uitaycompose.utils.tay_blue_800
import com.valu.uitaycompose.utils.tay_grey_300
import com.valu.uitaycompose.utils.tay_grey_400
import com.valu.uitaycompose.utils.tay_grey_50
import com.valu.uitaycompose.utils.tay_pink_400
import com.valu.uitaycompose.utils.tay_purple_700
import com.valu.uitaycompose.utils.textGabbi14
import com.valu.uitaycompose.utils.textGabbi18
import com.valu.uitaycompose.utils.textSe20

@Composable
fun ScreenInit() {
    val appDataVale = LocalAppDataVale.current
    val param = appDataVale.paramData
    val showExtraSocialButtons = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("screen_init_content")
    ) {
        MoviePlayerSection(idMovie = param.idMovie)
        MovieInfoSection(
            title = param.title,
            description = param.description
        )
        PrimarySocialLinksSection(param)
        if (showExtraSocialButtons) {
            ExtraSocialLinksSection()
        }
    }
}


@SuppressLint("ConfigurationScreenWidthHeight", "SetJavaScriptEnabled")
@Composable
fun MoviePlayerSection(idMovie: String) {
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx().toInt() }
    val calculatedHeight = (screenWidthPx / 0.15).toInt()
    val urlCompatibleConExoPlayer = "https://drive.google.com/uc?export=download&id=$idMovie"
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        border = BorderStroke(width = 2.dp, color = tay_grey_50)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(tay_grey_300),
            contentAlignment = Alignment.Center
        ) {

            VideoPlayerCompose(videoUrl = urlCompatibleConExoPlayer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) { calculatedHeight.toDp() })

            )
        }
    }
}

@Composable
fun MovieInfoSection(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            color = tay_pink_400,
            style = textGabbi18,
            maxLines = 2
        )

        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            color = tay_grey_400,
            style = textGabbi14
        )
    }
}

@Composable
fun PrimarySocialLinksSection(param: ParamModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SocialButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.name_face),
            textColor = tay_blue_800,
            iconRes = R.drawable.ic_facebook,
            onClick = {
                context.uiTayUrlFacebook(param.idFacebook)
            }
        )

        SocialButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.name_you),
            textColor = Color.Black,
            iconRes = R.drawable.ic_youtube,
            onClick = {
                context.uiTayOpenUrl("https://www.youtube.com/watch?v=${param.idYoutube?:"xH6qsMpA7NM"}")
            }
        )
    }
}

@Composable
fun ExtraSocialLinksSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SocialButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.name_tik),
            textColor = Color.Black,
            iconRes = R.drawable.ic_tiktok,
            onClick = { /* Acción Tiktok */ }
        )
        SocialButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.name_insta),
            textColor = tay_purple_700,
            iconRes = R.drawable.ic_instagram,
            onClick = { /* Acción Instagram */ }
        )
    }
}

@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    iconRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = textColor,
            style = textSe20
        )
    }
}