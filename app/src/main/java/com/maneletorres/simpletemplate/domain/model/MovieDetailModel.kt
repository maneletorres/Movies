package com.maneletorres.simpletemplate.domain.model

import android.os.Parcelable
import com.maneletorres.simpletemplate.domain.id.Identifiable
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