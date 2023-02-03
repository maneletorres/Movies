package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.common.DomainResponse

interface MovieDetailRepository {
    fun getMovie(id: String): DomainResponse<MovieDetailModel>
}