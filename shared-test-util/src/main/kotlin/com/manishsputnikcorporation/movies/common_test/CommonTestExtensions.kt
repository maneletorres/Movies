package com.manishsputnikcorporation.movies.common_test

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.DatabaseTV
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.network.response.tv.TVDto

fun MovieDto.toDatabase(isFavorite: Boolean = true): DatabaseMovie =
    DatabaseMovie(
        id = id ?: 0,
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        rating = rating ?: 0.0,
        isFavorite = isFavorite
    )

fun TVDto.toDatabase(isFavorite: Boolean = true): DatabaseTV =
    DatabaseTV(
        id = id ?: 0,
        originalName = originalName.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        name = name.orEmpty(),
        rating = rating ?: 0.0,
        isFavorite = isFavorite
    )

fun MovieDto.toModel(isFavorite: Boolean = true): MovieModel =
    MovieModel(
        id = id ?: 0,
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        rating = rating ?: 0.0,
        isFavorite = isFavorite
    )

fun TVDto.toModel(isFavorite: Boolean = true): TVModel =
    TVModel(
        id = id ?: 0,
        originalName = originalName.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        name = name.orEmpty(),
        rating = rating ?: 0.0,
        isFavorite = isFavorite
    )