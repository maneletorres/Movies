package com.manishsputnikcorporation.movies.network.response.tvshow

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVsDto(
    val page: Int?,
    val results: List<TVDto?>?,
    @field:Json(name = "total_pages") val totalPages: Int?,
    @field:Json(name = "total_results") val totalResults: Int?,
)
