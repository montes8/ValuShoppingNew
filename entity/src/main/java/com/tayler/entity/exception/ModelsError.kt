package com.tayler.entity.exception

data class UiTayApiException(
    val code: Int = 0,
    val title: String = "",
    val messageApi: String = ""
) : Exception()

class GenericException : Exception()

class MyNetworkException : Exception()

class OutOfHour : Exception()

class UnAuthorizedException : Exception()