package com.manishsputnikcorporation.movies.utils.enums

enum class Type(val type: String) {
    MOVIE(type = "Movie"),
    TV(type = "TV"),
}

fun String.toType(): Type =
    when (this) {
        Type.MOVIE.type -> Type.MOVIE
        Type.TV.type -> Type.TV
        else -> throw Exception() // TODO: to check
    }
