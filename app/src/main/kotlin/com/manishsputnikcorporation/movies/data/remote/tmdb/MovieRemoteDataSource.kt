package com.manishsputnikcorporation.movies.data.remote.tmdb

import com.manishsputnikcorporation.movies.BuildConfig
import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.CustomError
import com.manishsputnikcorporation.movies.network.CustomServicesExecutor
import com.manishsputnikcorporation.movies.network.response.tmdb.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.tmdb.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.service.tmdb.MoviesService
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import retrofit2.Call

interface MovieRemoteDataSource {

  @Throws(NetworkException::class, ServiceException::class)
  fun getPopularMovies(): Either<MoviesDto, CustomError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getPopularTVShows(): Either<MoviesDto, CustomError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getMovie(id: String): Either<MovieDetailDto, CustomError>
}

class MovieRemoteDataSourceImpl
@Inject
constructor(private val customService: CustomServicesExecutor, private val service: MoviesService) :
  MovieRemoteDataSource {

  @Throws(NetworkException::class, ServiceException::class)
  override fun getPopularMovies(): Either<MoviesDto, CustomError> {
    val call: Call<MoviesDto> = service.getPopularMovies(apiKey = BuildConfig.API_KEY)
    val response = customService.execute(call)
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getPopularTVShows(): Either<MoviesDto, CustomError> {
    val call: Call<MoviesDto> = service.getPopularTVShows(apiKey = BuildConfig.API_KEY)
    val response = customService.execute(call)
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getMovie(id: String): Either<MovieDetailDto, CustomError> {
    val call: Call<MovieDetailDto> = service.getMovie(id, BuildConfig.API_KEY, )
    val response = customService.execute(call)
    return response.mapSuccess { it.data }
  }
}
