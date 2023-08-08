package com.manishsputnikcorporation.movies.network.response.tmdb.movie

import com.squareup.moshi.Json

data class MovieDetailDto(
    @field:Json(name = "imdb_id") val id: String,
    val title: String,
    @field:Json(name = "original_title") val originalTitle: String,
    @field:Json(name = "poster_path") val image: String,
    @field:Json(name = "release_date") val releaseDate: String,
    val runtime: Int,
    val overview: String
)