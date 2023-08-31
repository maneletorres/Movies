package com.manishsputnikcorporation.movies.network.services.executor

import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.utils.Either

interface ServiceExecutor {

  @Throws(NetworkException::class, ServiceException::class)
  fun <T> execute(request: ServiceRequest<T>): Either<ServiceResponse<T>, ServiceError>
}

interface ServiceRequest<T>

open class ServiceResponse<T>(
    private val optionalData: T?,
    val statusCode: Int,
    val headers: Map<String, List<String>>
) {
  val data: T
    get() = optionalData!!
}

open class ServiceError(
    val description: String?,
    val statusCode: Int,
    val headers: Map<String, List<String>>
)
