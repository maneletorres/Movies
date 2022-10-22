package com.maneletorres.simpletemplate.network.rest.exceptions

import com.maneletorres.simpletemplate.domain.error.exception.CustomAppException

class TimeOutException(message: String, url: String? = null) : CustomAppException(message, url) {}