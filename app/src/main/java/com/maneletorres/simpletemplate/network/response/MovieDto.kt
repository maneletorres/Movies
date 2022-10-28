package com.maneletorres.simpletemplate.network.response

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("rankUpDown")
    val rankUpDown: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("fullTitle")
    val fullTitle: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("crew")
    val crew: String,
    @SerializedName("imdbRating")
    val imdbRating: String? = "",
    @SerializedName("imdbRatingCount")
    val imdbRatingCount: String? = ""
)