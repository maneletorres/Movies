package com.maneletorres.simpletemplate.domain.repository

import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.MovieDetailModel

interface MovieDetailRepository {
    fun getMovie(id: String): DomainResponse<MovieDetailModel>
}