package com.tayler.repository.network.base

import com.tayler.repository.utils.toAppException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

open class BaseNetwork @Inject constructor() {
    suspend fun <T> safeApiCall(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            throw e.toAppException()
        }
    }
}
