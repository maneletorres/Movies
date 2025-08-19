package com.manishsputnikcorporation.movies.data.remote

import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.service.RemoteApiService
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceRequest
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import retrofit2.Call

interface MovieRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class) fun getPopularMovies(): Either<MoviesDto, ServiceError>

    @Throws(NetworkException::class, ServiceException::class) fun getMovie(id: Int): Either<MovieDetailDto, ServiceError>
}

class MovieRemoteDataSourceImpl @Inject constructor(private val serviceExecutor: ServiceExecutor, private val service: RemoteApiService) :
    MovieRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class)
    override fun getPopularMovies(): Either<MoviesDto, ServiceError> {
        val call: Call<MoviesDto> = service.getPopularMovies()
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getMovie(id: Int): Either<MovieDetailDto, ServiceError> {
        val call: Call<MovieDetailDto> = service.getMovie(id)
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }
}
