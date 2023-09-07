package com.manishsputnikcorporation.movies.utils

sealed class Either<out T, out E> {
    data class Success<out T>(val data: T) : Either<T, Nothing>()

    data class Failure<out E>(val error: E) : Either<Nothing, E>()
}

fun eitherEmpty(): Either.Success<Unit> = Either.Success(Unit)

fun <T> eitherSuccess(data: T): Either.Success<T> = Either.Success(data)

fun <E> eitherFailure(error: E): Either.Failure<E> = Either.Failure(error)
