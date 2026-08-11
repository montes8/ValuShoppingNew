package com.tayler.valushopping.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.LocalAppDataVale
import com.valu.uitaycompose.swipe.UiTayInfoCompose
import com.valu.uitaycompose.utils.tay_grey_400
import com.valu.uitaycompose.utils.textSe16
import com.valu.uitaycompose.utils.textSeB16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryPointsBottomSheet(
    onDismissRequest: () -> Unit
) {
    val appDataVale = LocalAppDataVale.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {

                Text(
                    text = stringResource(id = R.string.text_title_delivery_points),
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = appDataVale.getColorPrincipal().first,
                    modifier = Modifier
                )

            Text(
                text = stringResource(id = R.string.text_country_pe),
                style = textSeB16,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.text_message_delivery_points),
                style = textSe16,
                color = tay_grey_400,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.text_country_ar),
                style = textSeB16,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.text_message_delivery_points_ar),
                style = textSe16,
                color = tay_grey_400,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UiTayInfoCompose(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 2.dp),
                text = stringResource(id = R.string.text_info_delivery_points),
            )
        }
    }
}