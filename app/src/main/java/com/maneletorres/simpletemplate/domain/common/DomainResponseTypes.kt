package com.maneletorres.simpletemplate.domain.common

import com.maneletorres.simpletemplate.utils.Either

data class DomainError(val code: String, val message: String)

typealias DomainResponse<T> = Either<T, DomainError>