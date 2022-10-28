package com.maneletorres.simpletemplate.network.response

data class MoviesDto(
    val items: List<MovieDto>,
    val errorMessage: String
)