package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.model.base.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailModel(
    override val id: Int,
    val imdbId: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val title: String,
    val rating: Double,
) : Identifiable, Parcelable
