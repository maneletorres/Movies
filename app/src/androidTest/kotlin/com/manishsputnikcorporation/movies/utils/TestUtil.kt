package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.utils.enums.Type

fun createEntity(id: String, type: Type): DatabaseMovie =
    DatabaseMovie(id, "fake-rank", "fake-title", "fake-year", "fake-imageUrl", "fake-rating", type)

fun createMovie(id: String = "fake-id") = createEntity(id = id, type = Type.MOVIE)

fun createSeries(id: String = "fake-id") = createEntity(id = id, type = Type.SERIES)
