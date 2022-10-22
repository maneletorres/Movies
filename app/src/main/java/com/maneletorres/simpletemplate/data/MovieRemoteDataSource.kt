package com.maneletorres.simpletemplate.data

import com.maneletorres.simpletemplate.domain.error.exception.NetworkException
import com.maneletorres.simpletemplate.domain.error.exception.ServiceException
import com.maneletorres.simpletemplate.network.CustomError
import com.maneletorres.simpletemplate.network.MoviesService
import com.maneletorres.simpletemplate.network.CustomServicesExecutor
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMovieDetailDto
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMovieDto
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MovieDto
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMoviesDto
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MoviesDto
import com.maneletorres.simpletemplate.network.util.API_KEY
import com.maneletorres.simpletemplate.network.util.IMDB_API_KEY
import com.maneletorres.simpletemplate.utils.Either
import com.maneletorres.simpletemplate.utils.mapSuccess
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRemoteDataSource {
    // region TheMovieDB:
    @Throws(NetworkException::class, ServiceException::class)
    fun getPopularMovies(): Either<MoviesDto, CustomError>

    @Throws(NetworkException::class, ServiceException::class)
    fun getTopRatedMovies(): Either<MoviesDto, CustomError>

    @Throws(NetworkException::class, ServiceException::class)
    fun getMovie(movieId: Int): Either<MovieDto, CustomError>
    // endregion

    // region IMDB:
    @Throws(NetworkException::class, ServiceException::class)
    fun getMostPopularMovies(): Either<ImdbMoviesDto, CustomError>

    @Throws(NetworkException::class, ServiceException::class)
    fun getMostPopularTVs(): Either<ImdbMoviesDto, CustomError>

    @Throws(NetworkException::class, ServiceException::class)
    fun getMovie(id: String): Either<ImdbMovieDetailDto, CustomError>
    // endregion
}

@Singleton
class MovieRemoteDataSourceImpl @Inject constructor(
    private val customService: CustomServicesExecutor,
    private val service: MoviesService
) : MovieRemoteDataSource {

    // region TheMovieDB:
    @Throws(NetworkException::class, ServiceException::class)
    override fun getPopularMovies(): Either<MoviesDto, CustomError> {
        val call: Call<MoviesDto> = service.getPopularMovies(API_KEY)
        val response = customService.execute(call)
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getTopRatedMovies(): Either<MoviesDto, CustomError> {
        val call: Call<MoviesDto> = service.getTopRatedMovies(API_KEY)
        val response = customService.execute(call)
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getMovie(movieId: Int): Either<MovieDto, CustomError> {
        val call: Call<MovieDto> = service.getMovie(movieId, API_KEY)
        val response = customService.execute(call)
        return response.mapSuccess { it.data }
    }
    // endregion

    // region IMDB:
    @Throws(NetworkException::class, ServiceException::class)
    override fun getMostPopularMovies(): Either<ImdbMoviesDto, CustomError> {
        val call: Call<ImdbMoviesDto> = service.getMostPopularMovies(IMDB_API_KEY)
        val response = customService.execute(call)
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getMostPopularTVs(): Either<ImdbMoviesDto, CustomError> {
        val call: Call<ImdbMoviesDto> = service.getMostPopularTVs(IMDB_API_KEY)
        val response = customService.execute(call)
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getMovie(id: String): Either<ImdbMovieDetailDto, CustomError> {
        val call: Call<ImdbMovieDetailDto> = service.getMovie(IMDB_API_KEY, id)
        val response = customService.execute(call)
        return response.mapSuccess { it.data }
    }
    // endregion
}