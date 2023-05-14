package com.manishsputnikcorporation.movies.network.response

import com.manishsputnikcorporation.movies.network.util.EMPTY_STRING
import com.squareup.moshi.Json

data class MovieDetailDto(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "originalTitle") val originalTitle: String,
    @field:Json(name = "fullTitle") val fullTitle: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "year") val year: String,
    @field:Json(name = "image") val image: String,
    @field:Json(name = "releaseDate") val releaseDate: String,
    @field:Json(name = "runtimeMins") val runtimeMins: String? = EMPTY_STRING,
    @field:Json(name = "runtimeStr") val runtimeStr: String? = null,
    @field:Json(name = "plot") val plot: String = EMPTY_STRING,
    @field:Json(name = "plotLocal") val plotLocal: String? = EMPTY_STRING,
    @field:Json(name = "plotLocalIsRtl") val plotLocalIsRtl: Boolean? = false,
    @field:Json(name = "directors") val directors: String
// TODO: to complete with all fields!
)
