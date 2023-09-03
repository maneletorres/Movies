package com.manishsputnikcorporation.movies.data.repository

import com.manishsputnikcorporation.movies.data.local.asDomainModel
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.TvShowLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.TvShowDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.TvShowDetailDtoMapper
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
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDetailMapper: MovieDetailDtoMapper,
    private val tvShowDetailMapper: TvShowDetailDtoMapper
) : MovieDetailRepository {

    // region Movie
    override fun getMovieDetail(id: String): DomainResponse<MovieDetailModel> =
        movieRemoteDataSource
            .getMovie(id)
            .mapSuccess { movieDetailDto ->
                return eitherSuccess(movieDetailMapper.mapToDomainModel(movieDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.description ?: ""))
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
    // endregion

    // region TVShow
    override fun getTvShowDetail(id: String): DomainResponse<TvShowDetailModel> =
        movieRemoteDataSource
            .getTVShow(id)
            .mapSuccess { tvShowDetailDto ->
                return eitherSuccess(tvShowDetailMapper.mapToDomainModel(tvShowDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.description ?: ""))
            }

    override fun isFavoriteTvShow(id: String): Flow<Boolean> = tvShowLocalDataSource.isFavoriteTvShow(id)

    override fun getTvShow(id: String) = tvShowLocalDataSource.getTvShow(id).map { it.asDomainModel() }

    override suspend fun updateTvShow(id: String): DomainResponse<Boolean> {
        val tvShow = tvShowLocalDataSource.getTvShow(id).first()
        val updatedTvShowFavoriteStatus: Boolean = !tvShow.isFavorite
        val updatedTvShow = tvShow.copy(isFavorite = updatedTvShowFavoriteStatus)
        return try {
            tvShowLocalDataSource.updateTvShow(updatedTvShow)
            eitherSuccess(updatedTvShowFavoriteStatus)
        } catch (ex: Exception) {
            eitherFailure(DomainError("Code: ", ex.message.toString()))
        }
    }
    // endregion
}
