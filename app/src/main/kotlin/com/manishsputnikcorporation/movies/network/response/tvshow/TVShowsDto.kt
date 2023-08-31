package com.manishsputnikcorporation.movies.network.response.tvshow

import com.squareup.moshi.Json

data class TVShowsDto(
    val page: Int,
    val results: List<TVShowDto>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int
)
