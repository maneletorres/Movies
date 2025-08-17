package com.manishsputnikcorporation.movies.domain.model.base

interface Identifiable {
    val id: Int
}

interface Model : Identifiable {
    val overview: String
    val popularity: Double
    val posterPath: String
    val releaseDate: String
    val rating: Double
    val isFavorite: Boolean
}
