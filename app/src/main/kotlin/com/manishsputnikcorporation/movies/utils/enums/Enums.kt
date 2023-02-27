package com.manishsputnikcorporation.movies.utils.enums

enum class Type(val type: String) {
  MOVIE("Movie"),
  SERIES("TVSeries")
}

fun String.toType(): Type =
    when (this) {
      Type.MOVIE.type -> Type.MOVIE
      Type.SERIES.type -> Type.SERIES
      else -> throw Exception() // TODO: to check
    }
