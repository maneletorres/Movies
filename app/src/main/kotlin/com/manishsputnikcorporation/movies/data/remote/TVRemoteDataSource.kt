package com.manishsputnikcorporation.movies.data.remote

import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto
import com.manishsputnikcorporation.movies.network.service.RemoteApiService
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceRequest
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import retrofit2.Call

interface TVRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class) fun getPopularTVs(): Either<TVsDto, ServiceError>

    @Throws(NetworkException::class, ServiceException::class) fun getTV(id: Int): Either<TVDetailDto, ServiceError>
}

class TVRemoteDataSourceImpl @Inject constructor(private val serviceExecutor: ServiceExecutor, private val service: RemoteApiService) :
    TVRemoteDataSource {

    @Throws(NetworkException::class, ServiceException::class)
    override fun getPopularTVs(): Either<TVsDto, ServiceError> {
        val call: Call<TVsDto> = service.getPopularTVs()
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }

    @Throws(NetworkException::class, ServiceException::class)
    override fun getTV(id: Int): Either<TVDetailDto, ServiceError> {
        val call: Call<TVDetailDto> = service.getTV(id)
        val response = serviceExecutor.execute(request = RetrofitServiceRequest(retrofitCall = call))
        return response.mapSuccess { it.data }
    }
}
