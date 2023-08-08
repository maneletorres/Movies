package com.manishsputnikcorporation.movies.network.response.tmdb.movie

import com.squareup.moshi.Json

data class MovieDto(
    val id: Int,
    val title: String,
    @field:Json(name = "original_title") val originalTitle: String,
    val overview: String,
    @field:Json(name = "poster_path") val imageUrl: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "vote_average") val rating: Double
)