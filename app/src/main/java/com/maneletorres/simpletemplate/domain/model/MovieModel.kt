package com.maneletorres.simpletemplate.domain.model

import android.os.Parcelable
import com.maneletorres.simpletemplate.domain.id.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    override val id: String,
    val title: String,
    val originalTitle: String,
    val overview: String? = null,
    val adult: Boolean,
    val popularity: Float,
    val posterPath: String? = null,
) : Identifiable, Parcelable