package com.tayler.valushopping.ui

import androidx.compose.runtime.Composable
import com.tayler.valushopping.component.ValeNavigationMain
import com.tayler.valushopping.ui.base.BaseActivity

class MainActivity : BaseActivity() {


    @Composable
    override fun SetScreenConfig() {
        ValeNavigationMain()
    }

    override fun setDataGlobal() {

    }

}

