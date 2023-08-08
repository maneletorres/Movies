package com.manishsputnikcorporation.movies.data.repository.imdb

import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.asDomainModel
import com.manishsputnikcorporation.movies.data.remote.imdb.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.movie.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.network.model.imdb.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MovieDetailRepositoryImpl
@Inject
constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieDetailDtoMapper
) : MovieDetailRepository {

  override fun getMovieDetail(id: String): DomainResponse<MovieDetailModel> =
      movieRemoteDataSource
          .getMovie(id)
          .mapSuccess { movieDetailDto ->
            return eitherSuccess(mapper.mapToDomainModel(movieDetailDto))
          }
          .mapFailure { error ->
            return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
          }

  override fun isFavoriteMovie(id: String): Flow<Boolean> = movieLocalDataSource.isFavoriteMovie(id)

  override fun getMovie(id: String) = movieLocalDataSource.getMovie(id).map { it.asDomainModel() }

  override suspend fun updateMovie(id: String): DomainResponse<Boolean> {
    val movie = movieLocalDataSource.getMovie(id).first()
    val updatedMovieFavoriteStatus: Boolean = !movie.isFavorite
    val updatedMovie = movie.copy(isFavorite = updatedMovieFavoriteStatus)
    return try {
      movieLocalDataSource.updateMovie(updatedMovie)
      eitherSuccess(updatedMovieFavoriteStatus)
    } catch (ex: Exception) {
      eitherFailure(DomainError("Code: ", ex.message.toString()))
    }
  }
}
