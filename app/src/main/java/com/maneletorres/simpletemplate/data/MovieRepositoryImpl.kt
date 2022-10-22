package com.maneletorres.simpletemplate.data

import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.IMDBMovieDetailModel
import com.maneletorres.simpletemplate.domain.model.IMDBMovieModel
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.domain.repository.MovieRepository
import com.maneletorres.simpletemplate.network.MoviesService
import com.maneletorres.simpletemplate.network.model.IMDBMovieDtoMapper
import com.maneletorres.simpletemplate.network.model.MovieDtoMapper
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MovieDto
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MoviesDto
import com.maneletorres.simpletemplate.utils.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieDtoMapper,
    private val imdbMapper: IMDBMovieDtoMapper
) : MovieRepository {

    // TheMovieDB:
    override fun getPopularMovies(): DomainResponse<List<MovieModel>> {
        return movieRemoteDataSource.getPopularMovies()
            .mapSuccess { moviesDto: MoviesDto ->
                return eitherSuccess(moviesDto.results.map { mapper.mapToDomainModel(it) }) }
            .mapFailure {
                return eitherFailure(DomainError(it.statusCode.toString(), it.raw))
            }
    }

    override fun getTopRatedMovies(): DomainResponse<List<MovieModel>> {
        return movieRemoteDataSource.getTopRatedMovies()
            .mapSuccess { moviesDto: MoviesDto ->
                return eitherSuccess(moviesDto.results.map { mapper.mapToDomainModel(it) }) }
            .mapFailure {
                return eitherFailure(DomainError(it.statusCode.toString(), it.raw))
            }
    }

    override fun getMovie(movieId: Int): DomainResponse<MovieModel> {
        return movieRemoteDataSource.getMovie(movieId)
            .mapSuccess { movieDto: MovieDto ->
                return eitherSuccess(mapper.mapToDomainModel(movieDto)) }
            .mapFailure {
                return eitherFailure(DomainError(it.statusCode.toString(), it.raw))
            }
    }

    // IMDB:
    override fun getMostPopularMovies(): DomainResponse<List<IMDBMovieModel>> {
        // TODO: only for test purposes
        val response = fakeImdbMoviesDto.items.map { imdbMapper.mapToDomainModel(it) }
        return eitherSuccess(response)

        return movieRemoteDataSource.getMostPopularMovies()
            .mapSuccess {
                return eitherSuccess(it.items.map { imdbMapper.mapToDomainModel(it) })
            }
            .mapFailure {
                return eitherFailure(DomainError(it.statusCode.toString(), it.raw))
            }
    }

    override fun getMostPopularTVs(): DomainResponse<List<IMDBMovieModel>> {
        return movieRemoteDataSource.getMostPopularTVs()
            .mapSuccess {
                return eitherSuccess(it.items.map { imdbMapper.mapToDomainModel(it) })
            }
            .mapFailure {
                return eitherFailure(DomainError(it.statusCode.toString(), it.raw))
            }
    }
}