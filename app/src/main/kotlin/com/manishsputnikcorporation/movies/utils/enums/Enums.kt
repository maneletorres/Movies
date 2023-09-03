package com.manishsputnikcorporation.movies.utils.enums

enum class Type(val type: String) {
    MOVIE("Movie"),
    TV_SHOW("TVSeries")
}

fun String.toType(): Type =
    when (this) {
        Type.MOVIE.type -> Type.MOVIE
        Type.TV_SHOW.type -> Type.TV_SHOW
        else -> throw Exception() // TODO: to check
    }
