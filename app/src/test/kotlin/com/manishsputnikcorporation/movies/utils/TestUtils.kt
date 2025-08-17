package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import kotlinx.coroutines.flow.flow

val fakeDomainError = eitherFailure(DomainError("fakeErrorCode", "fakeErrorMessage"))

val fakeFlowDomainError = flow { emit(fakeDomainError) }

val fakeCustomError = eitherFailure(error = ServiceError(description = "fakeCustomErrorRaw", statusCode = 0, headers = emptyMap()))
