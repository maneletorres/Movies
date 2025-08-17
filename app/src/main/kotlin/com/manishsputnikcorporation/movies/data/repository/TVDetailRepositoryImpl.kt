package com.manishsputnikcorporation.movies.data.repository

import com.manishsputnikcorporation.movies.data.local.toMovieModel
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.TvLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toTVModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.TvDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.TvDetailDtoMapper
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
    private val tvLocalDataSource: TvLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDetailMapper: MovieDetailDtoMapper,
    private val tvDetailMapper: TvDetailDtoMapper,
) : MovieDetailRepository {

    // region Movie
    override fun getMovieDetail(id: String): DomainResponse<MovieDetailModel> =
        movieRemoteDataSource
            .getMovie(id)
            .mapSuccess { movieDetailDto ->
                return eitherSuccess(data = movieDetailMapper.mapToDomainModel(model = movieDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(error = DomainError(code = error.statusCode.toString(), message = error.description ?: ""))
            }

    override fun isFavoriteMovie(id: String): Flow<Boolean> = movieLocalDataSource.isFavoriteMovie(id)

    override fun getMovie(id: String) = movieLocalDataSource.getMovie(id).map { it.toMovieModel() }

    override suspend fun updateMovie(id: String): DomainResponse<Boolean> {
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

    // endregion

    // region TV
    override fun getTVDetail(id: String): DomainResponse<TvDetailModel> =
        movieRemoteDataSource
            .getTV(id)
            .mapSuccess { tvDetailDto ->
                return eitherSuccess(data = tvDetailMapper.mapToDomainModel(model = tvDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(error = DomainError(code = error.statusCode.toString(), message = error.description ?: ""))
            }

    override fun isFavoriteTV(id: String): Flow<Boolean> = tvLocalDataSource.isFavoriteTv(id)

    override fun getTVById(id: String) = tvLocalDataSource.getTv(id).map { it.toTVModel() }

    override suspend fun updateTV(id: String): DomainResponse<Boolean> {
        val tv = tvLocalDataSource.getTv(id).first()
        val updatedTvFavoriteStatus: Boolean = !tv.isFavorite
        val updatedTv = tv.copy(isFavorite = updatedTvFavoriteStatus)
        return try {
            tvLocalDataSource.updateTv(updatedTv)
            eitherSuccess(data = updatedTvFavoriteStatus)
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }
    }
    // endregion
}
