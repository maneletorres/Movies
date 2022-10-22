package com.maneletorres.simpletemplate.network.response.imdb

data class ImdbMoviesDto(
    val items: List<ImdbMovieDto>,
    val errorMessage: String
)