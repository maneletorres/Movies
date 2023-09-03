package com.manishsputnikcorporation.movies.domain.id

interface Identifiable {
    val id: String
}

data class CustomIdentifiable(override val id: String) : Identifiable
