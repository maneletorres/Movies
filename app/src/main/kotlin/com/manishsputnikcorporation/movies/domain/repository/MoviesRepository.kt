package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

  val movies: Flow<List<MovieModel>>

  fun refreshMostPopularMovies(): DomainResponse<Unit>
}
