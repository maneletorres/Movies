package com.manishsputnikcorporation.movies.network.response.imdb

data class MoviesDto(val items: List<MovieDto>, val errorMessage: String)
