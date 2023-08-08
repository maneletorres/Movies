package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.movie.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
  fun getMovieDetail(id: String): DomainResponse<MovieDetailModel>

  fun isFavoriteMovie(id: String): Flow<Boolean>

  fun getMovie(id: String): Flow<MovieModel>

  suspend fun updateMovie(id: String): DomainResponse<Boolean>
}
