package com.manishsputnikcorporation.movies.network.response.tv

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVDetailDto(
    val id: Int?,
    @field:Json(name = "original_name") val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "first_air_date") val releaseDate: String?,
    val name: String?,
    @field:Json(name = "vote_average") val rating: Double?,
)
