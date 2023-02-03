package com.manishsputnikcorporation.movies.domain.error.exception

class ServiceException(message: String, url: String?) : CustomAppException(message, url)