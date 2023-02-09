package com.manishsputnikcorporation.movies.network.rest.exceptions

import com.manishsputnikcorporation.movies.domain.error.exception.CustomAppException

class TimeOutException(message: String, url: String? = null) : CustomAppException(message, url)
