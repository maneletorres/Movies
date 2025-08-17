package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.model.base.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvItemModel(
    override val id: Int,
    val originalName: String,
    val overview: String,
    val popularity: Float,
    val imageUrl: String,
    val releaseDate: String,
    val title: String,
    val rating: Float,
    val isFavorite: Boolean,
) : Identifiable, Parcelable