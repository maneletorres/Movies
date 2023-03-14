package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.utils.enums.Type
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

  // TODO: optimize the loadData and getData calls to this!!
  // val movies: Flow<List<MovieModel>>

  fun refreshMostPopularMovies(): DomainResponse<Unit>

  fun refreshMostPopularTVs(): DomainResponse<Unit>

  fun getData(searchType: Type): Flow<List<MovieModel>>

  fun getFavorites(): Flow<List<MovieModel>>
}
