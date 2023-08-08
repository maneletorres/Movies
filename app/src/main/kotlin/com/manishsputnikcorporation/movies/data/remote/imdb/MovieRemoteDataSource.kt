package com.manishsputnikcorporation.movies.data.remote.imdb

import com.manishsputnikcorporation.movies.BuildConfig
import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.CustomError
import com.manishsputnikcorporation.movies.network.CustomServicesExecutor
import com.manishsputnikcorporation.movies.network.service.imdb.MoviesService
import com.manishsputnikcorporation.movies.network.response.imdb.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.imdb.MoviesDto
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import retrofit2.Call

interface MovieRemoteDataSource {

  @Throws(NetworkException::class, ServiceException::class)
  fun getMostPopularMovies(): Either<MoviesDto, CustomError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getMostPopularTVs(): Either<MoviesDto, CustomError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getMovie(id: String): Either<MovieDetailDto, CustomError>
}

class MovieRemoteDataSourceImpl
@Inject
constructor(private val customService: CustomServicesExecutor, private val service: MoviesService) :
    MovieRemoteDataSource {

  @Throws(NetworkException::class, ServiceException::class)
  override fun getMostPopularMovies(): Either<MoviesDto, CustomError> {
    val call: Call<MoviesDto> = service.getMostPopularMovies(BuildConfig.API_KEY)
    val response = customService.execute(call)
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getMostPopularTVs(): Either<MoviesDto, CustomError> {
    val call: Call<MoviesDto> = service.getMostPopularTVs(BuildConfig.API_KEY)
    val response = customService.execute(call)
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getMovie(id: String): Either<MovieDetailDto, CustomError> {
    val call: Call<MovieDetailDto> = service.getMovie(BuildConfig.API_KEY, id)
    val response = customService.execute(call)
    return response.mapSuccess { it.data }
  }
}
