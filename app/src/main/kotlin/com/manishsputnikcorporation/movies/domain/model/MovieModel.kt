package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.id.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    override val id: String,
    val title: String,
    val year: String,
    val imageUrl: String,
    val rating: String, // TODO: to put a FAB in the item. Bottom right
) : Identifiable, Parcelable
