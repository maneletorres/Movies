package com.manishsputnikcorporation.movies.domain.model.tvshow

import android.os.Parcelable
import com.manishsputnikcorporation.movies.domain.id.Identifiable
import com.manishsputnikcorporation.movies.utils.enums.Type
import kotlinx.parcelize.Parcelize

@Parcelize
data class TVShowModel(
    override val id: String,
    val rank: String,
    val title: String,
    val year: String, // TODO: to extract info from releaseData
    val imageUrl: String,
    val rating: String, // TODO: to put a FAB in the item. Bottom right
    val isFavorite: Boolean
): Identifiable, Parcelable