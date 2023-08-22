package com.manishsputnikcorporation.movies.domain.model

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.id.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaItemModel(
    override val id: String,
    val rank: String,
    val title: String,
    val year: String,
    val imageUrl: String,
    val rating: String, // TODO: to put a FAB in the item. Bottom right
    val isFavorite: Boolean
) : Identifiable, Parcelable
