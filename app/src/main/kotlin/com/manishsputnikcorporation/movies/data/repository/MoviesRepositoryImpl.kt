package com.manishsputnikcorporation.movies.data.repository

import com.manishsputnikcorporation.movies.data.local.asDomainModel
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.TvShowLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowsDto
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl
@Inject
constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
) : MoviesRepository {

    // TODO: optimize the loadData and getData calls to these!!
    // override val movies = movieLocalDataSource.getMovies().map { it.asDomainModel() }
    // override val tvShows = tvShowLocalDataSource.getTvShows().map { it.asDomainModel() }

    override fun refreshPopularMovies(): DomainResponse<Unit> =
        movieRemoteDataSource
            .getPopularMovies()
            .mapSuccess { moviesDto ->
                return insertMovies(moviesDto)
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.description ?: ""))
            }

    override fun refreshPopularTVShows(): DomainResponse<Unit> =
        movieRemoteDataSource
            .getPopularTVShows()
            .mapSuccess { tvShowsDto ->
                return insertTvShows(tvShowsDto)
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.description ?: ""))
            }

    override fun getMovies(): Flow<List<MediaItemModel>> = movieLocalDataSource.getMovies().map { it.asDomainModel() }

    override fun getTvShows(): Flow<List<MediaItemModel>> = tvShowLocalDataSource.getTvShows().map { it.asDomainModel() }

    override fun getFavoriteMovies(): Flow<List<MediaItemModel>> = movieLocalDataSource.getFavoriteMovies().map { it.asDomainModel() }

    override fun getFavoriteTvShows(): Flow<List<MediaItemModel>> = tvShowLocalDataSource.getFavoriteTvShows().map { it.asDomainModel() }

    // region Helper methods
    private fun insertMovies(movies: MoviesDto): DomainResponse<Unit> =
        try {
            eitherSuccess(movieLocalDataSource.insertAllMovies(movies.asDatabaseModel()))
        } catch (ex: Exception) {
            eitherFailure(DomainError("Code: ", ex.message.toString()))
        }

    private fun insertTvShows(tvShows: TVShowsDto): DomainResponse<Unit> =
        try {
            eitherSuccess(tvShowLocalDataSource.insertTvShows(tvShows.asDatabaseModel()))
        } catch (ex: Exception) {
            eitherFailure(DomainError("Code: ", ex.message.toString()))
        }
    // endregion
}
