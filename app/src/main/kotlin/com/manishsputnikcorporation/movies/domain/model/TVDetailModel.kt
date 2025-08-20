package com.manishsputnikcorporation.movies.domain.model

data class TVDetailModel(
    override val id: Int,
    override val overview: String,
    override val popularity: Double,
    override val posterPath: String,
    override val releaseDate: String,
    override val rating: Double,
    override val isFavorite: Boolean,
    val originalName: String,
    val name: String,
) : DetailModel
