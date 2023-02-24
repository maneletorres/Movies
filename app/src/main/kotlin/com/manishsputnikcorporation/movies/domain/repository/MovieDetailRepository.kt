package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel

interface MovieDetailRepository {
  fun getMovie(id: String): DomainResponse<MovieDetailModel>
}
