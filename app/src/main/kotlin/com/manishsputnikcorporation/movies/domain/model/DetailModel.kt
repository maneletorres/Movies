package com.manishsputnikcorporation.movies.domain.model

import com.manishsputnikcorporation.movies.domain.model.base.Identifiable

interface DetailModel : Identifiable {
    override val id: Int
    val overview: String
    val popularity: Double
    val posterPath: String
    val releaseDate: String
    val rating: Double
    val isFavorite: Boolean
}
