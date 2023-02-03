package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.common.DomainResponse

interface MovieRepository {

    fun getMostPopularMovies(): DomainResponse<List<MovieModel>>

    fun getMostPopularTVs(): DomainResponse<List<MovieModel>>
}