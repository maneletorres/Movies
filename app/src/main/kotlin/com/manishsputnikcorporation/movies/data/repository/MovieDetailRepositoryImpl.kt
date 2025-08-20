package com.manishsputnikcorporation.movies.data.repository

import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toMovieModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

class MovieDetailRepositoryImpl
@Inject
constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDetailMapper: MovieDetailDtoMapper,
) : MovieDetailRepository {

    override fun getMovieDetail(id: Int): DomainResponse<MovieDetailModel> =
        movieRemoteDataSource
            .getMovie(id)
            .mapSuccess { movieDetailDto ->
                return eitherSuccess(data = movieDetailMapper.mapToDomainModel(dto = movieDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(error = DomainError(code = error.statusCode.toString(), message = error.description ?: ""))
            }

    override fun isFavoriteMovie(id: Int): Flow<Boolean> = movieLocalDataSource.isFavoriteMovie(id)

    override fun getMovie(id: Int): Flow<MovieModel> =
        movieLocalDataSource.getMovie(id).transform { databaseMovie -> emit(value = databaseMovie.toMovieModel()) }

    override suspend fun updateMovie(id: Int): DomainResponse<Boolean> {
        val movie = movieLocalDataSource.getMovie(id).first()
        val updatedMovieFavoriteStatus: Boolean = !movie.isFavorite
        val updatedMovie = movie.copy(isFavorite = updatedMovieFavoriteStatus)
        return try {
            movieLocalDataSource.updateMovie(updatedMovie)
            eitherSuccess(data = updatedMovieFavoriteStatus)
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }
    }
}
