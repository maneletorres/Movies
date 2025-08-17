package com.manishsputnikcorporation.movies.network

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.DatabaseTV
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) // TODO: is it necessary?
data class NetworkMovieContainer(val movies: List<MovieDto>)

fun MoviesDto.asDatabaseModel(): List<DatabaseMovie> =
    results
        ?.map {
            DatabaseMovie(
                id = it?.id ?: 0,
                originalTitle = it?.originalTitle.orEmpty(),
                overview = it?.overview.orEmpty(),
                popularity = it?.popularity ?: 0.0,
                posterPath = it?.posterPath.orEmpty(),
                releaseDate = it?.releaseDate.orEmpty(),
                title = it?.title.orEmpty(),
                rating = it?.rating ?: 0.0,
                isFavorite = false,
            )
        }
        .orEmpty()

fun TVsDto.asDatabaseModel(): List<DatabaseTV> =
    results
        ?.map {
            DatabaseTV(
                id = it?.id ?: 0,
                originalName = it?.originalName.orEmpty(),
                overview = it?.overview.orEmpty(),
                popularity = it?.popularity ?: 0.0,
                posterPath = it?.posterPath.orEmpty(),
                releaseDate = it?.releaseDate.orEmpty(),
                name = it?.name.orEmpty(),
                rating = it?.rating ?: 0.0,
                isFavorite = false,
            )
        }
        .orEmpty()
