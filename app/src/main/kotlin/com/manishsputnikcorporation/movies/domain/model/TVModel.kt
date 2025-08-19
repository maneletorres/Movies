package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.model.base.Model
import kotlinx.parcelize.Parcelize

@Parcelize
data class TVModel(
    override val id: Int,
    override val overview: String,
    override val popularity: Double,
    override val posterPath: String,
    override val releaseDate: String,
    override val rating: Double,
    override val isFavorite: Boolean,
    val originalName: String,
    val name: String,
) : Model, Parcelable
