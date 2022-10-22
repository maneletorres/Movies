package com.maneletorres.simpletemplate.network.response.theMoviesDB

data class MoviesDto(
    //@SerializedName("page")
    var page: Int,

    //@SerializedName("results")
    var results: List<MovieDto>
)