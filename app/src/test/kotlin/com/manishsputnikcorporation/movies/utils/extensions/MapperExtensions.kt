package com.manishsputnikcorporation.movies.utils.extensions

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.domain.model.MovieModel
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

fun MovieDto.asDomainModel(isFavorite: Boolean = true): MovieModel =
    MovieModel(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        type = type,
        isFavorite = isFavorite)
