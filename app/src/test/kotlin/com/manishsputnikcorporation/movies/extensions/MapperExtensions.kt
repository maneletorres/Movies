package com.manishsputnikcorporation.movies.extensions

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.network.response.MovieDto

fun MovieDto.asDatabaseModel(isFavorite: Boolean = true): DatabaseMovie =
    DatabaseMovie(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        type = type,
        isFavorite = isFavorite)
