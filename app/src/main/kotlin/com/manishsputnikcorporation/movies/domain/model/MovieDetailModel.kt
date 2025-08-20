package com.manishsputnikcorporation.movies.domain.model

data class MovieDetailModel(
    override val id: Int,
    override val overview: String,
    override val popularity: Double,
    override val posterPath: String,
    override val releaseDate: String,
    override val rating: Double,
    override val isFavorite: Boolean,
    val imdbId: String,
    val originalTitle: String,
    val runtime: Int,
    val title: String,
) : DetailModel
