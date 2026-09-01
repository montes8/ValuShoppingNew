package com.tayler.core.network.protocol

import com.tayler.core.model.DataLogin
import com.tayler.core.model.ParamModel

interface IUserNetwork {
    suspend fun loadParam(id:String): ParamModel
}