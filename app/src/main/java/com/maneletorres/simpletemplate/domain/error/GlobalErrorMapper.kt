package com.maneletorres.simpletemplate.domain.error

import com.maneletorres.simpletemplate.domain.error.exception.NetworkException
import com.maneletorres.simpletemplate.domain.error.exception.UnauthorizedException
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

interface GlobalErrorMapper {
    fun map(throwable: Throwable): GlobalErrorType
}

@Singleton
class GlobalErrorMapperImpl @Inject constructor() : GlobalErrorMapper {

    override fun map(throwable: Throwable): GlobalErrorType = when (throwable) {
        is UnauthorizedException -> GlobalErrorType.UNAUTHORIZED
        is NetworkException -> GlobalErrorType.NETWORK_UNAVAILABLE
        is CancellationException,
        is IllegalStateException -> GlobalErrorType.SILENT
        else -> GlobalErrorType.GENERIC_ERROR
    }
}