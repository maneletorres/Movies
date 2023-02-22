package com.manishsputnikcorporation.movies.network.response

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id") val id: String,
    @Json(name = "rank") val rank: String,
    @Json(name = "rankUpDown") val rankUpDown: String,
    @Json(name = "title") val title: String,
    @Json(name = "fullTitle") val fullTitle: String,
    @Json(name = "year") val year: String,
    @Json(name = "image") val image: String,
    @Json(name = "crew") val crew: String,
    @Json(name = "imdbRating") val imdbRating: String? = "",
    @Json(name = "imdbRatingCount") val imdbRatingCount: String? = ""
)
