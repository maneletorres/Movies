package com.manishsputnikcorporation.movies.data.remote

import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowDetailDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowsDto
import com.manishsputnikcorporation.movies.network.service.MoviesService
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceRequest
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import retrofit2.Call

interface MovieRemoteDataSource {

  @Throws(NetworkException::class, ServiceException::class)
  fun getPopularMovies(): Either<MoviesDto, ServiceError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getPopularTVShows(): Either<TVShowsDto, ServiceError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getMovie(id: String): Either<MovieDetailDto, ServiceError>

  @Throws(NetworkException::class, ServiceException::class)
  fun getTVShow(id: String): Either<TVShowDetailDto, ServiceError>
}

class MovieRemoteDataSourceImpl
@Inject
constructor(private val serviceExecutor: ServiceExecutor, private val service: MoviesService) :
    MovieRemoteDataSource {

  @Throws(NetworkException::class, ServiceException::class)
  override fun getPopularMovies(): Either<MoviesDto, ServiceError> {
    val call: Call<MoviesDto> = service.getPopularMovies()
    val response = serviceExecutor.execute(RetrofitServiceRequest(call))
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getPopularTVShows(): Either<TVShowsDto, ServiceError> {
    val call: Call<TVShowsDto> = service.getPopularTVShows()
    val response = serviceExecutor.execute(RetrofitServiceRequest(call))
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getMovie(id: String): Either<MovieDetailDto, ServiceError> {
    val call: Call<MovieDetailDto> = service.getMovie(id)
    val response = serviceExecutor.execute(RetrofitServiceRequest(call))
    return response.mapSuccess { it.data }
  }

  @Throws(NetworkException::class, ServiceException::class)
  override fun getTVShow(id: String): Either<TVShowDetailDto, ServiceError> {
    val call: Call<TVShowDetailDto> = service.getTVShow(id)
    val response = serviceExecutor.execute(RetrofitServiceRequest(call))
    return response.mapSuccess { it.data }
  }
}
