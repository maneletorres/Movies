package com.manishsputnikcorporation.movies.network.response.tvshow

import com.squareup.moshi.Json

data class TVShowDto(
    val id: Int,
    @field:Json(name = "name") val title: String,
    @field:Json(name = "original_name") val originalTitle: String,
    val overview: String,
    @field:Json(name = "poster_path") val imageUrl: String,
    @field:Json(name = "first_air_date") val releaseDate: String,
    @field:Json(name = "vote_average") val rating: Double,
)
