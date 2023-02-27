package com.manishsputnikcorporation.movies.data

import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.asDomainModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.response.MoviesDto
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl
@Inject
constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MoviesRepository {

  // TODO: optimize the loadData and getData calls to this!!
  override val movies = movieLocalDataSource.getMovies(Type.MOVIE).map { it.asDomainModel() }

  override fun refreshMostPopularMovies(): DomainResponse<Unit> =
      movieRemoteDataSource
          .getMostPopularMovies()
          .mapSuccess { moviesDto ->
            return insertMovies(moviesDto, Type.MOVIE)
          }
          .mapFailure { error ->
            return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
          }

  override fun refreshMostPopularTVs(): DomainResponse<Unit> =
      movieRemoteDataSource
          .getMostPopularTVs()
          .mapSuccess { moviesDto ->
            return insertMovies(moviesDto, Type.SERIES)
          }
          .mapFailure { error ->
            return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
          }

  override fun getData(searchType: Type): Flow<List<MovieModel>> =
      movieLocalDataSource.getMovies(searchType).map { it.asDomainModel() }

  // region Helper methods
  private fun insertMovies(movies: MoviesDto, type: Type): DomainResponse<Unit> =
      try {
        eitherSuccess(movieLocalDataSource.insertAll(movies.asDatabaseModel(type)))
      } catch (ex: Exception) {
        eitherFailure(DomainError("Code: ", ex.message.toString()))
      }
  // endregion
}
