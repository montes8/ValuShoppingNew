package com.tayler.valushopping.entity

import android.content.Context
import com.tayler.entity.ParamModel
import com.tayler.valushopping.utils.*
import com.valu.uitaycompose.utils.extension.getNameBackgroundCustom
import com.valu.uitaycompose.utils.extension.getNameSplashCustom
import com.valu.uitaycompose.utils.extension.getNameToolbarCustom
import com.valu.uitaycompose.utils.extension.uiTayFormatTwelveHour
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppDataValeTest {

    private lateinit var appDataVale: AppDataVale
    private val context: Context = mockk()

    @Before
    fun setUp() {
        appDataVale = AppDataVale()
        // Correct class names from the library
        mockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionUtilsKt")
        mockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionDateKt")
    }

    @After
    fun tearDown() {
        unmockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionUtilsKt")
        unmockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionDateKt")
    }

    @Test
    fun getColorPrincipal_returnsCorrectColorsForPink() {
        appDataVale.paramData = ParamModel(styleValu = "0")
        val (primary, secondary, bg) = appDataVale.getColorPrincipal()
        assertEquals(color_principal_pink, primary)
        assertEquals(color_secondary_pink, secondary)
        assertEquals(color_principal_pink_bg, bg)
    }

    @Test
    fun getColorPrincipal_returnsCorrectColorsForRed() {
        appDataVale.paramData = ParamModel(styleValu = "1")
        val (primary, _, _) = appDataVale.getColorPrincipal()
        assertEquals(color_principal_red, primary)
    }

    @Test
    fun getColorPrincipal_returnsDefaultWhenStyleInvalid() {
        appDataVale.paramData = ParamModel(styleValu = "99")
        val (primary, _, _) = appDataVale.getColorPrincipal()
        assertEquals(color_principal_pink, primary)
    }

    @Test
    fun mapperDialogText_formatsCorrectlly() {
        every { any<String>().uiTayFormatTwelveHour() } returns "formatted-time"
        
        appDataVale.paramData = ParamModel(hourStart = "09:00", hourEnd = "18:00")
        val text = appDataVale.mapperDialogText()
        assertTrue(text.contains("formatted-time"))
    }

    @Test
    fun getUrlBgSplash_returnsUrlWhenServiceEnabled() {
        appDataVale.paramData = ParamModel(bgService = true, urlImage = "http://test.com/")
        every { context.getNameSplashCustom() } returns "my_splash"

        val url = appDataVale.getUrlBgSplash(context)
        assertEquals("http://test.com/uploads/banners/my_splash/my_splash.png", url)
    }

    @Test
    fun getUrlBgSplash_returnsEmptyWhenServiceDisabled() {
        appDataVale.paramData = ParamModel(bgService = false)
        val url = appDataVale.getUrlBgSplash(context)
        assertEquals("", url)
    }

    @Test
    fun getUrlBg_returnsUrlWhenServiceEnabled() {
        appDataVale.paramData = ParamModel(bgService = true, urlImage = "http://test.com/")
        every { context.getNameBackgroundCustom() } returns "my_bg"

        val url = appDataVale.getUrlBg(context)
        assertEquals("http://test.com/uploads/banners/my_bg/my_bg.png", url)
    }

    @Test
    fun getUrlBgToolbar_returnsUrlWhenServiceEnabled() {
        appDataVale.paramData = ParamModel(bgService = true, urlImage = "http://test.com/")
        every { context.getNameToolbarCustom() } returns "my_toolbar"

        val url = appDataVale.getUrlBgToolbar(context)
        assertEquals("http://test.com/uploads/banners/my_toolbar/my_toolbar.png", url)
    }
}
