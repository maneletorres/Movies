package com.manishsputnikcorporation.movies.utils.extensions

import com.manishsputnikcorporation.movies.utils.Either
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind.AT_MOST_ONCE
import kotlin.contracts.contract

// region Side-effects
@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.onSuccess(action: (T) -> Unit): Either<T, E> {
    contract { callsInPlace(action, AT_MOST_ONCE) }

    if (this is Either.Success) action(data)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.onFailure(action: (E) -> Unit): Either<T, E> {
    contract { callsInPlace(action, AT_MOST_ONCE) }

    if (this is Either.Failure) action(error)
    return this
}
// endregion

// region Transformers
@OptIn(ExperimentalContracts::class)
inline fun <T1, E1, T2, E2> Either<T1, E1>.map(onSuccess: (T1) -> T2, onFailure: (E1) -> E2): Either<T2, E2> {
    contract {
        callsInPlace(onSuccess, AT_MOST_ONCE)
        callsInPlace(onFailure, AT_MOST_ONCE)
    }

    return when (this) {
        is Either.Success -> Either.Success(onSuccess(data))
        is Either.Failure -> Either.Failure(onFailure(error))
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T1, E, T2> Either<T1, E>.mapSuccess(action: (T1) -> T2): Either<T2, E> {
    contract { callsInPlace(action, AT_MOST_ONCE) }

    return when (this) {
        is Either.Success -> Either.Success(action(data))
        is Either.Failure -> Either.Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T, E1, E2> Either<T, E1>.mapFailure(action: (E1) -> E2): Either<T, E2> {
    contract { callsInPlace(action, AT_MOST_ONCE) }

    return when (this) {
        is Either.Success -> Either.Success(data)
        is Either.Failure -> Either.Failure(action(error))
    }
}
// endregion

// region Concat
@OptIn(ExperimentalContracts::class)
inline fun <T1, E, T2> Either<T1, E>.then(onSuccess: (T1) -> Either<T2, E>): Either<T2, E> {
    contract { callsInPlace(onSuccess, AT_MOST_ONCE) }

    return when (this) {
        is Either.Success -> onSuccess(data)
        is Either.Failure -> Either.Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T1, E1, T2, E2> Either<T1, E1>.then(onSuccess: (T1) -> Either<T2, E2>, onFailure: (E1) -> Either<T2, E2>): Either<T2, E2> {

    contract {
        callsInPlace(onSuccess, AT_MOST_ONCE)
        callsInPlace(onFailure, AT_MOST_ONCE)
    }

    return when (this) {
        is Either.Success -> onSuccess(data)
        is Either.Failure -> onFailure(error)
    }
}
// endregion

// region Retrieve
fun <T, E> Either<T, E>.getOrNull() = if (this is Either.Success) data else null

fun <T, E> Either<T, E>.getOrThrow() = if (this is Either.Success) data else error("Response must be Success")

fun <T, E> Either<T, E>.getOrElse(value: T) = if (this is Either.Success) data else value

@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.getOrElse(action: () -> T): T {
    contract { callsInPlace(action, AT_MOST_ONCE) }

    return if (this is Either.Success) data else action()
}
// endregion

// region Checks
inline val Either<*, *>.isSuccess
    get() = this is Either.Success

inline val Either<*, *>.isFailure
    get() = this is Either.Failure
// endregion
