package com.manishsputnikcorporation.movies.utils.extensions

const val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

fun isValidEmail(email: String): Boolean = email.matches(emailRegex.toRegex())

fun isValidPassword(password: String): Boolean = password.length >= 6
