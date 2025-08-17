package com.manishsputnikcorporation.movies.network.response.tvshow

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVDto(
    val id: Int?,
    @field:Json(name = "original_name") val originalName: String?,
    val overview: String?,
    val popularity: Float?,
    @field:Json(name = "poster_path") val imageUrl: String?,
    @field:Json(name = "first_air_date") val releaseDate: String?,
    @field:Json(name = "name") val title: String?,
    @field:Json(name = "vote_average") val rating: Float?,
)