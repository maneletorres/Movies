package com.manishsputnikcorporation.movies.domain.error.exception

import java.lang.Exception

abstract class CustomAppException(message: String, val url: String? = null) : Exception(message)
