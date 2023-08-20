package com.manishsputnikcorporation.movies.domain.error.exception

import java.lang.Exception

abstract class GenericException(message: String, val url: String? = null) : Exception(message)

class NetworkException(message: String) : GenericException(message)

class ServiceException(message: String, url: String?) : GenericException(message, url)

class TimeOutException(message: String, url: String? = null) : GenericException(message, url)

class UnauthorizedException(message: String) : GenericException(message)
