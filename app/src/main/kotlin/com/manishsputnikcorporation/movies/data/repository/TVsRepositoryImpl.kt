package com.manishsputnikcorporation.movies.data.repository

import com.manishsputnikcorporation.movies.data.local.toMovieModel
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.TvLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toTVModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class MoviesRepositoryImpl
@Inject
constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val tvLocalDataSource: TvLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
) : MoviesRepository {

    // TODO: optimize the loadData and getData calls to these!!
    // override val movies = movieLocalDataSource.getMovies().map { it.asDomainModel() }
    // override val tvs = tvLocalDataSource.getTvs().map { it.asDomainModel() }

    override fun refreshPopularMovies(): DomainResponse<Unit> =
        movieRemoteDataSource
            .getPopularMovies()
            .mapSuccess { moviesDto ->
                return insertMovies(moviesDto)
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.description ?: ""))
            }

    override fun refreshPopularTVs(): DomainResponse<Unit> =
        movieRemoteDataSource
            .getPopularTVs()
            .mapSuccess { tvsDto ->
                return insertTvs(tvsDto)
            }
            .mapFailure { error ->
                return eitherFailure(
                    error = DomainError(
                        code = error.statusCode.toString(),
                        message = error.description ?: ""
                    )
                )
            }

    override fun getMovies(): Flow<List<MovieModel>> = movieLocalDataSource.getMovies().map { flow ->
        flow.map { databaseMovie ->
            databaseMovie.toMovieModel()
        }
    }

    override fun getTVs(): Flow<List<TVModel>> = tvLocalDataSource.getTvs().map { flow ->
        flow.map { databaseTv ->
            databaseTv.toTVModel()
        }
    }

    override fun getFavoriteMovies(): Flow<List<MovieModel>> = movieLocalDataSource.getFavoriteMovies().map { flow ->
        flow.map { databaseMovie ->
            databaseMovie.toMovieModel()
        }
    }

    override fun getFavoriteTVs(): Flow<List<TVModel>> = tvLocalDataSource.getFavoriteTvs().map { flow ->
        flow.map { databaseTv ->
            databaseTv.toTVModel()
        }
    }

    // region Helper methods
    private fun insertMovies(movies: MoviesDto): DomainResponse<Unit> =
        try {
            eitherSuccess(data = movieLocalDataSource.insertAllMovies(movies = movies.asDatabaseModel()))
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }

    private fun insertTvs(tvs: TVsDto): DomainResponse<Unit> =
        try {
            eitherSuccess(data = tvLocalDataSource.insertTvs(tvs = tvs.asDatabaseModel()))
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }
    // endregion
}
