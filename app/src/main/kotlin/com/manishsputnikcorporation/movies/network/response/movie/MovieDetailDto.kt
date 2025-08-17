package com.manishsputnikcorporation.movies.network.response.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailDto(
    val id: Int?,
    @field:Json(name = "imdb_id") val imdbId: String?,
    @field:Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "release_date") val releaseDate: String?,
    val runtime: Int?,
    val title: String?,
    @field:Json(name = "vote_average") val rating: Double?,
)
