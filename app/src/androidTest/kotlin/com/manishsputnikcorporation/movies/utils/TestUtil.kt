package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*

fun createEntity(id: String, type: Type, isFavorite: Boolean = false): DatabaseMovie =
    DatabaseMovie(
        id,
        "fake-rank",
        "fake-title",
        "fake-year",
        "fake-imageUrl",
        "fake-rating",
        type,
        isFavorite)

fun createMovie(id: String = "fake-id", isFavorite: Boolean = false) =
    createEntity(id, MOVIE, isFavorite)

fun createSeries(id: String = "fake-id", isFavorite: Boolean = false) =
    createEntity(id, SERIES, isFavorite)
