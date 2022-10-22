package com.maneletorres.simpletemplate.network.response.theMoviesDB

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("popularity")
    var popularity: Float,
    @SerializedName("poster_path")
    var image: String?
)