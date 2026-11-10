package com.manishsputnikcorporation.movies.data.repository

import androidx.annotation.VisibleForTesting
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toMovieModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlin.collections.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

class MoviesRepositoryImpl
@Inject
constructor(private val movieLocalDataSource: MovieLocalDataSource, private val movieRemoteDataSource: MovieRemoteDataSource) :
    MoviesRepository {

    // TODO: optimize the loadData and getData calls to these!!
    // override val movies = movieLocalDataSource.getMovies().map { it.asDomainModel() }

    override suspend fun refreshPopularMovies(): DomainResponse<Unit> =
        movieRemoteDataSource
            .getPopularMovies()
            .mapSuccess { moviesDto ->
                val favoriteStates = movieLocalDataSource.getMovies().first().associate { it.id to it.isFavorite }
                return insertMovies(movies = moviesDto, favoriteStates = favoriteStates)
            }
            .mapFailure { error ->
                return eitherFailure(error = DomainError(code = error.statusCode.toString(), message = error.description ?: ""))
            }

    override fun getMovies(): Flow<List<MovieModel>> =
        movieLocalDataSource.getMovies().transform { databaseMovies ->
            emit(value = databaseMovies.map { databaseMovie -> databaseMovie.toMovieModel() })
        }

    override fun getFavoriteMovies(): Flow<List<MovieModel>> =
        movieLocalDataSource.getFavoriteMovies().transform { databaseFavoriteMovies ->
            emit(value = databaseFavoriteMovies.map { databaseMovie -> databaseMovie.toMovieModel() })
        }

    // region Helper methods
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertMovies(movies: MoviesDto, favoriteStates: Map<Int, Boolean> = emptyMap()): DomainResponse<Unit> =
        try {
            val databaseMovies =
                movies.asDatabaseModel().map { movie -> movie.copy(isFavorite = favoriteStates[movie.id] ?: movie.isFavorite) }
            eitherSuccess(data = movieLocalDataSource.insertAll(movies = databaseMovies))
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }
    // endregion
}
