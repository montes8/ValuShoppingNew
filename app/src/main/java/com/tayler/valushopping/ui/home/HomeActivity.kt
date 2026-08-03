package com.tayler.valushopping.ui.home

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import com.tayler.valushopping.component.ValeNavigationMain
import com.tayler.valushopping.ui.base.BaseActivity

class HomeActivity : BaseActivity() {

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
           context.startActivity(intent)
        }
    }

    @Composable
    override fun SetScreenConfig() {
        ValeNavigationMain()
    }

    override fun setDataGlobal() {

    }

}