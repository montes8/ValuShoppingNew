package com.tayler.core.network.base

import com.tayler.core.network.utils.toAppException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

open class BaseNetwork @Inject constructor() {
    suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            throw e.toAppException()
        }
    }
}
