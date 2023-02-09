package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel

interface MovieRepository {

  fun getMostPopularMovies(): DomainResponse<List<MovieModel>>

  fun getMostPopularTVs(): DomainResponse<List<MovieModel>>
}
