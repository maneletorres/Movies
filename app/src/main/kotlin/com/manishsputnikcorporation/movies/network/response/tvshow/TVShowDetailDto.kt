package com.manishsputnikcorporation.movies.network.response.tvshow

import com.squareup.moshi.Json

data class TVShowDetailDto(
    val id: Int,
    @field:Json(name = "name") val title: String,
    @field:Json(name = "original_name") val originalTitle: String,
    @field:Json(name = "poster_path") val image: String,
    @field:Json(name = "first_air_date") val releaseDate: String,
    @field:Json(name = "episode_run_time") val runtime: List<Int>,
    val overview: String,
)
