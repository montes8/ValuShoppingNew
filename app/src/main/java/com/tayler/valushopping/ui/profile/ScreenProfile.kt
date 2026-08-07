package com.tayler.valushopping.ui.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tayler.entity.UserModel
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.valu.uitaycompose.button.UiTayButton
import com.valu.uitaycompose.label.UiTayEditLayout
import com.valu.uitaycompose.utils.UI_EMPTY
import com.valu.uitaycompose.utils.extension.uiTayConverterCircle
import com.valu.uitaycompose.utils.extension.uiTayValidateEmail
import com.valu.uitaycompose.utils.extension.uiTayValidatePhoneFormat
import com.valu.uitaycompose.utils.permission.UiTayCameraManagerCompose
import com.valu.uitaycompose.utils.permission.rememberUiTayCameraManager
import com.valu.uitaycompose.utils.textGabbiB20
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ScreenProfile(
    onNavigateBack: () -> Unit
) {
    val viewModel: UserViewModel = hiltViewModel()
    val user by viewModel.successUserState.collectAsState()
    var userModel by remember { mutableStateOf(UserModel()) }
    var isEditing by remember { mutableStateOf(false) }
    var typeBanner by remember { mutableStateOf(true) }
    var nameText by remember { mutableStateOf(UI_EMPTY) }
    var lastNameText by remember { mutableStateOf(UI_EMPTY) }
    var documentText by remember { mutableStateOf(UI_EMPTY) }
    var emailText by remember { mutableStateOf(UI_EMPTY) }
    var phoneText by remember { mutableStateOf(UI_EMPTY) }
    var addressText by remember { mutableStateOf(UI_EMPTY) }
    var bannerBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var profileBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(user) {
        user?.let {
            userModel = it
            nameText = it.names
            lastNameText = it.lastName
            documentText = it.document
            emailText = it.email
            phoneText = it.phone
            addressText = it.address
            withContext(Dispatchers.IO) {
                if (it.imgBanner.isNotEmpty()) {
                    bannerBitmap = BitmapFactory.decodeFile(it.imgBanner).asImageBitmap()
                }
                if (it.img.isNotEmpty()) {
                    profileBitmap = BitmapFactory.decodeFile(it.img).uiTayConverterCircle().asImageBitmap()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    val cameraManager = rememberUiTayCameraManager(
        uiTayNameFilePath = "user",
        listener = object : UiTayCameraManagerCompose.CameraControllerListener {
            override fun onCameraPermissionDenied() {}

            override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
                if (typeBanner) {
                    userModel.imgBanner = path
                    bannerBitmap = img.asImageBitmap()
                } else {
                    userModel.img = path
                    profileBitmap = img.uiTayConverterCircle().asImageBitmap()
                }
                viewModel.saveUserImg(userModel)
            }
        }
    )

    val isButtonEnabled = remember(nameText, lastNameText, documentText, emailText, phoneText, addressText) {
        var flagEnable = 0
        flagEnable += if (nameText.isNotEmpty()) 0 else 1
        flagEnable += if (lastNameText.isNotEmpty()) 0 else 1
        flagEnable += if (documentText.length == 8) 0 else 1
        flagEnable += if (emailText.uiTayValidateEmail()) 0 else 1
        flagEnable += if (phoneText.uiTayValidatePhoneFormat()) 0 else 1
        flagEnable += if (addressText.isNotEmpty()) 0 else 1
        flagEnable == 0
    }

    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFEDEFFC),
                                        Color(0xFFDADDF4)
                                    )
                                )
                            )
                            .clickable {
                                typeBanner = true
                                cameraManager.doCamera("userBannerImg", isBanner = true)
                            }
                    ) {
                        if (bannerBitmap != null) {
                            Image(
                                bitmap = bannerBitmap!!,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera_banner),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 36.dp, end = 16.dp),
                            tint = AppDataVale.getColorPrincipal().first
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_star),
                        contentDescription = "Volver",
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp)
                            .size(33.dp)
                            .align(Alignment.TopStart)
                            .clickable { onNavigateBack.invoke() }
                    )

                    if (profileBitmap == null) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile_place_holder),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.BottomEnd)
                                .padding(end = 24.dp)
                                .clickable {
                                    typeBanner = false
                                    cameraManager.doCamera("userImg", isBanner = false)
                                }
                        )
                    } else {
                        Image(
                            bitmap = profileBitmap!!,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.BottomEnd)
                                .padding(end = 24.dp)
                                .clickable {
                                    typeBanner = false
                                    cameraManager.doCamera("userImg", isBanner = false)
                                }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable { isEditing = !isEditing }
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Datos Personales",
                                style = textGabbiB20,
                                color = AppDataVale.getColorPrincipal().first
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            if (!isEditing){
                                Image(
                                    painter = painterResource(id = R.drawable.ic_edit),
                                    contentDescription = "Editar",
                                    modifier = Modifier.size(33.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    UiTayEditLayout(
                        value = nameText,
                        onValueChange = { nameText = it },
                        hint = "Nombres",
                        enabled = isEditing,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UiTayEditLayout(
                        value = lastNameText,
                        onValueChange = { lastNameText = it },
                        hint = "Apellidos",
                        enabled = isEditing,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UiTayEditLayout(
                        value = documentText,
                        onValueChange = { documentText = it },
                        hint = "Numero de documento",
                        enabled = isEditing,
                        maxLength = 8,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UiTayEditLayout(
                        value = emailText,
                        onValueChange = { emailText = it },
                        hint = "Correo electronico",
                        enabled = isEditing,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UiTayEditLayout(
                        value = phoneText,
                        onValueChange = { phoneText = it },
                        hint = "Telefono",
                        enabled = isEditing,
                        maxLength = 9,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UiTayEditLayout(
                        value = addressText,
                        onValueChange = { addressText = it },
                        hint = "Dirección",
                        enabled = isEditing,
                        imeAction = ImeAction.Done,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isEditing) {
                        Spacer(modifier = Modifier.height(40.dp))

                        UiTayButton(
                            uiTayText = "Guardar",
                            uiTayEnable = isButtonEnabled
                        ) {
                            userModel.names = nameText
                            userModel.lastName = lastNameText
                            userModel.document = documentText
                            userModel.email = emailText
                            userModel.phone = phoneText
                            userModel.address = addressText
                            viewModel.saveUser(userModel)
                            isEditing = false
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}