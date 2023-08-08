package com.manishsputnikcorporation.movies.data.repository.imdb

import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.asDomainModel
import com.manishsputnikcorporation.movies.data.remote.imdb.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.model.tvshow.TVShowModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.network.asDatabaseMovieModel
import com.manishsputnikcorporation.movies.network.asDatabaseTVShowModel
import com.manishsputnikcorporation.movies.network.response.imdb.MoviesDto
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
  override val movies = movieLocalDataSource.getMovies().map { it.asDomainModel() }

  override val tvShows: Flow<List<TVShowModel>> = movieLocalDataSource.getTVShows().map { it.asDomainModel() }

  override fun refreshPopularMovies(): DomainResponse<Unit> =
      movieRemoteDataSource
          .getMostPopularMovies()
          .mapSuccess { moviesDto ->
            return insertMovies(moviesDto, Type.MOVIE)
          }
          .mapFailure { error ->
            return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
          }

  override fun refreshPopularTVShows(): DomainResponse<Unit> =
      movieRemoteDataSource
          .getMostPopularTVs()
          .mapSuccess { tvShows ->
            return insertMovies(tvShows, Type.SERIES)
          }
          .mapFailure { error ->
            return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
          }

  override fun getMovies(): Flow<List<MovieModel>> =
      movieLocalDataSource.getMovies().map { it.asDomainModel() }

  override fun getTVShows(): Flow<List<TVShowModel>> =
      movieLocalDataSource.getTVShows().map { it.asDomainModel() }

  override fun getFavoriteMovies(): Flow<List<MovieModel>> =
      movieLocalDataSource.getFavoriteMovies().map { it.asDomainModel() }

  override fun getFavoriteTVShows(): Flow<List<TVShowModel>> =
      movieLocalDataSource.getFavoriteTVShows().map { it.asDomainModel() }

  // region Helper methods
  private fun insertMovies(movies: MoviesDto, type: Type): DomainResponse<Unit> =
      try {
          eitherSuccess(when (type) {
              Type.MOVIE -> movieLocalDataSource.insertAllMovies(movies.asDatabaseMovieModel())
              Type.SERIES -> movieLocalDataSource.insertAllTVShows(movies.asDatabaseTVShowModel())
          })
      } catch (ex: Exception) {
        eitherFailure(DomainError("Code: ", ex.message.toString()))
      }
  // endregion
}
