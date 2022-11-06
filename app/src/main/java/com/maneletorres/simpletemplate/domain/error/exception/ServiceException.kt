package com.maneletorres.simpletemplate.domain.error.exception

class ServiceException(message: String, url: String?) : CustomAppException(message, url)