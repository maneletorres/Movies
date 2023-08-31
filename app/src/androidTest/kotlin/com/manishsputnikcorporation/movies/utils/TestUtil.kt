package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.DatabaseTvShow

fun createMovie(id: String = "fake-id", isFavorite: Boolean = false) =
    DatabaseMovie(
        id, "fake-rank", "fake-title", "fake-year", "fake-imageUrl", "fake-rating", isFavorite)

fun createTvShow(id: String = "fake-id", isFavorite: Boolean = false) =
    DatabaseTvShow(
        id, "fake-rank", "fake-title", "fake-year", "fake-imageUrl", "fake-rating", isFavorite)
