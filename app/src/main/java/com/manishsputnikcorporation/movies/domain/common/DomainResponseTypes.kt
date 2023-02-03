package com.manishsputnikcorporation.movies.domain.common

import com.manishsputnikcorporation.movies.utils.Either

data class DomainError(val code: String, val message: String)

typealias DomainResponse<T> = Either<T, DomainError>