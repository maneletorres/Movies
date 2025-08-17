package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.model.base.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowDetailModel(
    override val id: String,
    val title: String,
    val originalTitle: String,
    val fullTitle: String,
    val plot: String,
    val image: String,
    val releaseDate: String,
    val runtimeStr: List<String>,
) : Identifiable, Parcelable
