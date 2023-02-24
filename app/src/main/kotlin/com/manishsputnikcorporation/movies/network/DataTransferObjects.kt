package com.manishsputnikcorporation.movies.network

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.network.response.MovieDto
import com.manishsputnikcorporation.movies.network.response.MoviesDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) // TODO: is it necessary?
data class NetworkMovieContainer(val movies: List<MovieDto>)

fun MoviesDto.asDatabaseModel(): List<DatabaseMovie> {
  return items.map {
    DatabaseMovie(
        id = it.id, title = it.title, year = it.year, imageUrl = it.imageUrl, rating = it.rating)
  }
}
