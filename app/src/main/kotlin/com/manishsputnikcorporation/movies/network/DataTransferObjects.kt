package com.manishsputnikcorporation.movies.network

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.DatabaseTVShow
import com.manishsputnikcorporation.movies.network.response.imdb.MovieDto
import com.manishsputnikcorporation.movies.network.response.tmdb.tvshow.TVShowsDto
import com.squareup.moshi.JsonClass
import com.manishsputnikcorporation.movies.network.response.imdb.MoviesDto as IMDBMoviesDto
import com.manishsputnikcorporation.movies.network.response.tmdb.movie.MoviesDto as TMDBMoviesDto

@JsonClass(generateAdapter = true) // TODO: is it necessary?
data class NetworkMovieContainer(val movies: List<MovieDto>)

fun IMDBMoviesDto.asDatabaseMovieModel(): List<DatabaseMovie> = items.map {
    DatabaseMovie(
        id = it.id,
        rank = it.rank,
        title = it.title,
        year = it.year,
        imageUrl = it.imageUrl,
        rating = it.rating,
        isFavorite = false)
  }

fun IMDBMoviesDto.asDatabaseTVShowModel(): List<DatabaseTVShow> = items.map {
    DatabaseTVShow(
        id = it.id,
        rank = it.rank,
        title = it.title,
        year = it.year,
        imageUrl = it.imageUrl,
        rating = it.rating,
        isFavorite = false)
}

fun TMDBMoviesDto.asDatabaseModel(): List<DatabaseMovie> = results.map {
    DatabaseMovie(
        id = it.id.toString(),
        rank = "", // it.rank, // TODO: to check
        title = it.title,
        year = it.releaseDate, // TODO: to extract
        imageUrl = it.imageUrl,
        rating = it.rating.toString(),
        isFavorite = false)
}

fun TVShowsDto.asDatabaseModel(): List<DatabaseTVShow> = results.map {
    DatabaseTVShow(
        id = it.id.toString(),
        rank = "", // it.rank, // TODO: to check
        title = it.title,
        year = it.releaseDate, // TODO: to extract
        imageUrl = it.imageUrl,
        rating = it.rating.toString(),
        isFavorite = false)
}
