package com.manishsputnikcorporation.movies.network.response

import com.squareup.moshi.Json

data class MovieDetailDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "originalTitle") val originalTitle: String,
    @Json(name = "fullTitle") val fullTitle: String,
    @Json(name = "year") val year: String,
    @Json(name = "image") val image: String,
    @Json(name = "releaseDate") val releaseDate: String,
    @Json(name = "runtimeMins") val runtimeMins: String? = "",
    @Json(name = "runtimeStr") val runtimeStr: String? = null,
    @Json(name = "plot") val plot: String = "",
    @Json(name = "plotLocal") val plotLocal: String? = "",
    @Json(name = "plotLocalIsRtl") val plotLocalIsRtl: Boolean? = false,
    @Json(name = "directors") val directors: String
// TODO: to complete with all fields!
)
