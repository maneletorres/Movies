package com.manishsputnikcorporation.movies.domain.model.movie

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.id.Identifiable
import com.manishsputnikcorporation.movies.utils.enums.Type
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailModel(
    override val id: String,
    val title: String,
    val originalTitle: String,
    val fullTitle: String,
    val year: String,
    val plot: String,
    val image: String,
    val releaseDate: String,
    val runtimeStr: String?,
    val directors: String
) : Identifiable, Parcelable
