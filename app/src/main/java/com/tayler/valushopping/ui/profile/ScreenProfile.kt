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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tayler.entity.UserModel
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.LocalAppDataVale
import com.valu.uitaycompose.button.UiTayButton
import com.valu.uitaycompose.label.UiTayEditLayout
import com.valu.uitaycompose.utils.extension.uiTayConverterCircle
import com.valu.uitaycompose.utils.permission.UiTayCameraManagerCompose
import com.valu.uitaycompose.utils.permission.rememberUiTayCameraManager
import com.valu.uitaycompose.utils.textGabbiB20
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ScreenProfile(
    onNavigateBack: () -> Unit
) {
    val appDataVale = LocalAppDataVale.current
    val viewModel: UserViewModel = hiltViewModel()

    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val isButtonEnabled by viewModel.isFormValid.collectAsStateWithLifecycle()

    var isEditing by remember { mutableStateOf(false) }
    var typeBanner by remember { mutableStateOf(true) }
    var bannerBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var profileBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(formState.imgBanner, formState.img) {
        loadUserBitmaps(formState) { banner, profile ->
            bannerBitmap = banner
            profileBitmap = profile
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
                    bannerBitmap = img.asImageBitmap()
                    viewModel.saveUserImg(formState.copy(imgBanner = path))
                } else {
                    profileBitmap = img.uiTayConverterCircle().asImageBitmap()
                    viewModel.saveUserImg(formState.copy(img = path))
                }
            }
        }
    )

    Scaffold(topBar = {}) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .testTag("profile_scroll_content"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileHeaderSection(
                    bannerBitmap = bannerBitmap,
                    profileBitmap = profileBitmap,
                    primaryColor = appDataVale.getColorPrincipal().first,
                    onBackClick = onNavigateBack,
                    onBannerClick = {
                        typeBanner = true
                        cameraManager.doCamera("userBannerImg", isBanner = true)
                    },
                    onProfileClick = {
                        typeBanner = false
                        cameraManager.doCamera("userImg", isBanner = false)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileFormSection(
                    viewModel = viewModel,
                    formState = formState,
                    isButtonEnabled = isButtonEnabled,
                    primaryColor = appDataVale.getColorPrincipal().first,
                    isEditing = isEditing,
                    onEditToggle = { isEditing = !isEditing },
                    onSaveSuccess = { isEditing = false }
                )
            }
        }
    }
}

private suspend fun loadUserBitmaps(
    user: UserModel,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    onBitmapsLoaded: (ImageBitmap?, ImageBitmap?) -> Unit
) {
    withContext(ioDispatcher) {
        val banner = if (user.imgBanner.isNotEmpty()) {
            BitmapFactory.decodeFile(user.imgBanner)?.asImageBitmap()
        } else null

        val profile = if (user.img.isNotEmpty()) {
            BitmapFactory.decodeFile(user.img)?.uiTayConverterCircle()?.asImageBitmap()
        } else null

        onBitmapsLoaded(banner, profile)
    }
}

@Composable
private fun ProfileFormSection(
    viewModel: UserViewModel,
    formState: UserModel,
    isButtonEnabled: Boolean,
    primaryColor: Color,
    isEditing: Boolean,
    onEditToggle: () -> Unit,
    onSaveSuccess: () -> Unit
) {
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
                    .clickable { onEditToggle() }
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.title_data),
                    style = textGabbiB20,
                    color = primaryColor
                )

                Spacer(modifier = Modifier.width(6.dp))

                if (!isEditing) {
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
            value = formState.names,
            onValueChange = { viewModel.onNameChanged(it) },
            hint = stringResource(R.string.hint_names),
            enabled = isEditing,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        UiTayEditLayout(
            value = formState.lastName,
            onValueChange = { viewModel.onLastNameChanged(it) },
            hint = stringResource(R.string.hint_lastname),
            enabled = isEditing,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        UiTayEditLayout(
            value = formState.document,
            onValueChange = { viewModel.onDocumentChanged(it) },
            hint = stringResource(R.string.hint_doc),
            enabled = isEditing,
            maxLength = 8,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        UiTayEditLayout(
            value = formState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            hint = stringResource(R.string.hint_email),
            enabled = isEditing,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        UiTayEditLayout(
            value = formState.phone,
            onValueChange = { viewModel.onPhoneChanged(it) },
            hint = stringResource(R.string.hint_phone),
            enabled = isEditing,
            maxLength = 9,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        UiTayEditLayout(
            value = formState.address,
            onValueChange = { viewModel.onAddressChanged(it) },
            hint = stringResource(R.string.hint_address),
            enabled = isEditing,
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth()
        )

        if (isEditing) {
            Spacer(modifier = Modifier.height(40.dp))

            UiTayButton(
                uiTayText = stringResource(R.string.btn_save),
                uiTayEnable = isButtonEnabled,
                uiTayClick = {
                    viewModel.saveUser(formState)
                    onSaveSuccess()
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ProfileHeaderSection(
    bannerBitmap: ImageBitmap?,
    profileBitmap: ImageBitmap?,
    primaryColor: Color,
    onBackClick: () -> Unit,
    onBannerClick: () -> Unit,
    onProfileClick: () -> Unit
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
                        colors = listOf(Color(0xFFEDEFFC), Color(0xFFDADDF4))
                    )
                )
                .clickable { onBannerClick() }
        ) {
            if (bannerBitmap != null) {
                Image(
                    bitmap = bannerBitmap,
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
                tint = primaryColor
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_star),
            contentDescription = "Volver",
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .size(33.dp)
                .align(Alignment.TopStart)
                .clickable { onBackClick() }
        )

        val profileModifier = Modifier
            .size(90.dp)
            .align(Alignment.BottomEnd)
            .padding(end = 24.dp)
            .clickable { onProfileClick() }

        if (profileBitmap == null) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_place_holder),
                contentDescription = null,
                modifier = profileModifier
            )
        } else {
            Image(
                bitmap = profileBitmap,
                contentDescription = null,
                modifier = profileModifier
            )
        }
    }
}