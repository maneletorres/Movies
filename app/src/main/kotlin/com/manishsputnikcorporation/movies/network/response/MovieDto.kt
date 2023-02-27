package com.manishsputnikcorporation.movies.network.response

import com.squareup.moshi.Json

data class MovieDto(
    val id: String,
    val title: String,
    val year: String,
    @field:Json(name = "image") val imageUrl: String,
    @field:Json(name = "imDbRating") val rating: String,
)
