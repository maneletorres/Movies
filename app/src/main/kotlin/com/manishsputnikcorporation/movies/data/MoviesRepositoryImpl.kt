package com.manishsputnikcorporation.movies.data

import android.database.sqlite.SQLiteException
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.asDomainMovieModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.response.MoviesDto
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl
@Inject
constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MoviesRepository {

  override val movies = movieLocalDataSource.getMovies().map { it.asDomainMovieModel() }

  override fun refreshMostPopularMovies(): DomainResponse<Unit> {
    // TODO: only for test purposes
    // return insertMovies(fakeMovieListDto, DatabaseMovieType.MOVIE)

    return movieRemoteDataSource
        .getMostPopularMovies()
        .mapSuccess { moviesDto ->
          return insertMovies(moviesDto, Type.MOVIE)
        }
        .mapFailure { error ->
          return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
        }
  }

  // TODO: to add a MenuItem to change the API search call
  override fun refreshMostPopularTVs(): DomainResponse<Unit> {
    // TODO: only for test purposes
    // return insertMovies(fakeSeriesListDto, DatabaseMovieType.SERIES)

    return movieRemoteDataSource
        .getMostPopularTVs()
        .mapSuccess { moviesDto ->
          return insertMovies(moviesDto, Type.SERIES)
        }
        .mapFailure { error ->
          return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
        }
  }

  // TODO: are necessary these try-catch clauses?
  private fun insertMovies(movies: MoviesDto, type: Type): DomainResponse<Unit> =
      try {
        eitherSuccess(movieLocalDataSource.insertAll(movies.asDatabaseModel(type)))
      } catch (ex: SQLiteException) {
        eitherFailure(DomainError("", ex.message.toString()))
      } catch (ex: IOException) {
        eitherFailure(DomainError("", ex.message.toString()))
      } catch (ex: Exception) {
        eitherFailure(DomainError("", ex.message.toString()))
      }
}
