package com.manishsputnikcorporation.movies.network.response

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("originalTitle") val originalTitle: String,
    @SerializedName("fullTitle") val fullTitle: String,
    @SerializedName("year") val year: String,
    @SerializedName("image") val image: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("runtimeMins") val runtimeMins: String? = "",
    @SerializedName("runtimeStr") val runtimeStr: String? = null,
    @SerializedName("plot") val plot: String = "",
    @SerializedName("plotLocal") val plotLocal: String? = "",
    @SerializedName("plotLocalIsRtl") val plotLocalIsRtl: String? = "",
    @SerializedName("directors") val directors: String
// TODO: to complete with all fields!
)
