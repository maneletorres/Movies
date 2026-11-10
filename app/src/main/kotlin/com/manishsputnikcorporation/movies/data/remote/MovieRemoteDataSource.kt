package com.manishsputnikcorporation.movies.data.remote

import android.content.Context
import com.manishsputnikcorporation.movies.BuildConfig.BEARER_TOKEN
import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.service.RemoteApiService
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceRequest
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import javax.inject.Inject

interface MovieRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class) fun getPopularMovies(): Either<MoviesDto, ServiceError>

    @Throws(NetworkException::class, ServiceException::class) fun getMovie(id: Int): Either<MovieDetailDto, ServiceError>
}

class MovieRemoteDataSourceImpl @Inject constructor(
    private val serviceExecutor: ServiceExecutor,
    private val service: RemoteApiService,
    @param:ApplicationContext private val context: Context,
) : MovieRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class)
    override fun getPopularMovies(): Either<MoviesDto, ServiceError> {
        if (BEARER_TOKEN.isEmpty()) {
            val localResponse = LocalJsonAssetReader.readListAsset(
                context = context,
                fileName = "mock/movies/popular_movies.json",
                clazz = MoviesDto::class.java,
                missingMessage = "Missing JSON for movies",
            )
            if (localResponse.isSuccess) return localResponse
        }

        val call: Call<MoviesDto> = service.getPopularMovies()
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getMovie(id: Int): Either<MovieDetailDto, ServiceError> {
        if (BEARER_TOKEN.isEmpty()) {
            val localResponse = LocalJsonAssetReader.readDetailAsset(
                context = context,
                assetRoot = "mock/movies",
                id = id,
                clazz = MovieDetailDto::class.java,
                missingMessage = "Missing JSON for movie detail",
            )
            if (localResponse.isSuccess) return localResponse
        }

        val call: Call<MovieDetailDto> = service.getMovie(id)
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }
}
