package com.manishsputnikcorporation.movies.network

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.DatabaseTvShow
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowsDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) // TODO: is it necessary?
data class NetworkMovieContainer(val movies: List<MovieDto>)

fun MoviesDto.asDatabaseModel(): List<DatabaseMovie> =
    results.map {
      DatabaseMovie(
          id = it.id.toString(),
          rank = "", // it.rank, // TODO: to check
          title = it.title,
          year = it.releaseDate, // TODO: to extract
          imageUrl = it.imageUrl,
          rating = it.rating.toString(),
          isFavorite = false)
    }

fun TVShowsDto.asDatabaseModel(): List<DatabaseTvShow> =
    results.map {
      DatabaseTvShow(
          id = it.id.toString(),
          rank = "", // it.rank, // TODO: to check
          title = it.title,
          year = it.releaseDate, // TODO: to extract
          imageUrl = it.imageUrl,
          rating = it.rating.toString(),
          isFavorite = false)
    }
