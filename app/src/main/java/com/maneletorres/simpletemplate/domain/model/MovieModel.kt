package com.maneletorres.simpletemplate.domain.model

import android.os.Parcelable
import com.maneletorres.simpletemplate.domain.id.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    override val id: String,
    val rank: String,
    val rankUpDown: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imdbRating: String? = "",
    val imdbRatingCount: String? = ""
) : Identifiable, Parcelable