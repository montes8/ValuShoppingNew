import com.tayler.core.common.utils.*
package com.tayler.ui

import com.tayler.core.common.utils.*

import android.content.Context
import com.tayler.core.model.ParamModel
import com.tayler.core.model.exception.MyNetworkException
import com.tayler.core.model.exception.OutOfHour
import com.tayler.core.model.exception.UiTayApiException
import com.tayler.core.model.exception.UnAuthorizedException
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

class ExtensionsTest {

    private val context: Context = mockk()
    private val appDataVale = AppDataVale()

    @Test
    fun `mapperError returns correct values for different exceptions`() {
        every { context.getString(R.string.error_text_connection) } returns "Connection Error"
        every { context.getString(R.string.error_message_connection) } returns "No Internet"
        
        val networkError = MyNetworkException().mapperError(context, appDataVale)
        assertEquals(R.drawable.ic_error_red, networkError.first)
        assertEquals("Connection Error", networkError.second)

        val apiError = UiTayApiException(1, "API Title", "API Msg").mapperError(context, appDataVale)
        assertEquals("API Title", apiError.second)
        assertEquals("API Msg", apiError.third)

        every { context.getString(R.string.error_text_hour) } returns "Hour Error"
        val hourError = OutOfHour().mapperError(context, appDataVale)
        assertEquals("Hour Error", hourError.second)
    }

    @Test
    fun `validateHourApp returns true when within range`() {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        val now = dateFormat.format(Date())
        val start = "00:00:00"
        val end = "23:59:59"
        
        val param = ParamModel(hourStart = start, hourEnd = end)
        assertTrue(param.validateHourApp())
    }

    @Test
    fun `setImageMenu returns correct drawable for styles`() {
        appDataVale.paramData = ParamModel(styleValu = "1")
        assertEquals(R.drawable.ic_menu_home_1, setImageMenu(appDataVale))

        appDataVale.paramData = ParamModel(styleValu = "10")
        assertEquals(R.drawable.ic_menu_home_10, setImageMenu(appDataVale))

        appDataVale.paramData = ParamModel(styleValu = "invalid")
        assertEquals(R.drawable.ic_menu_home, setImageMenu(appDataVale))
    }

    @Test
    fun `setImageLogout returns correct drawable for styles`() {
        appDataVale.paramData = ParamModel(styleValu = "2")
        assertEquals(R.drawable.ic_logout_2, setImageLogout(appDataVale))

        appDataVale.paramData = ParamModel(styleValu = "17")
        assertEquals(R.drawable.ic_logout_17, setImageLogout(appDataVale))
    }

    @Test
    fun `getDrawableResId returns correct resource`() {
        assertEquals(R.drawable.ic_home, getDrawableResId("ic_home"))
        assertEquals(R.drawable.ic_profile, getDrawableResId("ic_profile"))
        assertEquals(R.drawable.ic_home, getDrawableResId("unknown"))
    }
}
