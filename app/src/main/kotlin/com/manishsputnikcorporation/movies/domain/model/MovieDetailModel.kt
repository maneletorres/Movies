package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.model.base.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailItemModel(
    override val id: Int,
    val imdbId: String,
    val originalTitle: String,
    val plot: String,
    val popularity: Float,
    val image: String,
    val releaseDate: String,
    val runtime: Int,
    val title: String,
    val rating: Float,
) : Identifiable, Parcelable
