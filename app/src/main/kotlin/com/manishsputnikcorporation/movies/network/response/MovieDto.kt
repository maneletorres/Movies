package com.manishsputnikcorporation.movies.network.response

import com.manishsputnikcorporation.movies.utils.enums.Type
import com.squareup.moshi.Json

data class MovieDto(
    val id: String,
    val rank: String,
    val title: String,
    val year: String,
    @field:Json(name = "image") val imageUrl: String,
    @field:Json(name = "imDbRating") val rating: String,
    val type: Type
)
