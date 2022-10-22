package com.maneletorres.simpletemplate.ui.detail.carousel

import com.maneletorres.simpletemplate.domain.id.Identifiable
import com.maneletorres.simpletemplate.domain.model.MovieModel

interface CarouselMovieItem : Identifiable {

}

data class RegularCarouselMovieItem(
    override val id: String,
    val posterPath: String? = null
) : CarouselMovieItem

fun MovieModel.toCarouselCustomItem(): CarouselMovieItem = RegularCarouselMovieItem(
    id = id,
    posterPath = posterPath
)