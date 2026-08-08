package com.tayler.repository.network.base

import com.tayler.repository.utils.toAppException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

open class BaseNetwork @Inject constructor() {
    inline suspend fun <T> safeApiCall(crossinline block: suspend () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            throw e.toAppException()
        }
    }
}
