package com.manishsputnikcorporation.movies.domain.error

import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.UnauthorizedException
import java.util.concurrent.CancellationException
import javax.inject.Inject

interface GlobalErrorMapper {
    fun map(throwable: Throwable): GlobalErrorType
}

class GlobalErrorMapperImpl @Inject constructor() : GlobalErrorMapper {

    override fun map(throwable: Throwable): GlobalErrorType =
        when (throwable) {
            is UnauthorizedException -> GlobalErrorType.UNAUTHORIZED
            is NetworkException -> GlobalErrorType.NETWORK_UNAVAILABLE
            is CancellationException,
            is IllegalStateException -> GlobalErrorType.SILENT
            else -> GlobalErrorType.GENERIC_ERROR
        }
}
