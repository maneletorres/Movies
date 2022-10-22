package com.maneletorres.simpletemplate.domain.error.exception

import java.lang.Exception

abstract class CustomAppException(message: String, val url: String? = null) : Exception(message)