package com.manishsputnikcorporation.movies.data.remote

import android.content.Context
import com.manishsputnikcorporation.movies.BuildConfig.DEBUG
import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto
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

interface TVRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class) fun getPopularTVs(): Either<TVsDto, ServiceError>

    @Throws(NetworkException::class, ServiceException::class) fun getTV(id: Int): Either<TVDetailDto, ServiceError>
}

class TVRemoteDataSourceImpl @Inject constructor(
    private val serviceExecutor: ServiceExecutor,
    private val service: RemoteApiService,
    @param:ApplicationContext private val context: Context,
) : TVRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class)
    override fun getPopularTVs(): Either<TVsDto, ServiceError> {
        if (DEBUG) {
            val localResponse = LocalJsonAssetReader.readListAsset(
                context = context,
                fileName = "mock/tvshows/popular_tv_shows.json",
                clazz = TVsDto::class.java,
                missingMessage = "Missing JSON for TV shows",
            )
            if (localResponse.isSuccess) return localResponse
        }

        val call: Call<TVsDto> = service.getPopularTVs()
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getTV(id: Int): Either<TVDetailDto, ServiceError> {
        if (DEBUG) {
            val localResponse = LocalJsonAssetReader.readDetailAsset(
                context = context,
                assetRoot = "mock/tvshows",
                id = id,
                clazz = TVDetailDto::class.java,
                missingMessage = "Missing JSON for TV detail",
            )
            if (localResponse.isSuccess) return localResponse
        }

        val call: Call<TVDetailDto> = service.getTV(id)
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }
}