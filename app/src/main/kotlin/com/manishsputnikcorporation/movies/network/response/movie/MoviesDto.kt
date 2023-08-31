package com.manishsputnikcorporation.movies.network.response.movie

import com.squareup.moshi.Json

data class MoviesDto(
    val page: Int,
    val results: List<MovieDto>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int
)
