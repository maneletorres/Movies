package com.maneletorres.simpletemplate.domain.repository

import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.MovieModel

interface MovieRepository {

    fun getMostPopularMovies(): DomainResponse<List<MovieModel>>

    fun getMostPopularTVs(): DomainResponse<List<MovieModel>>
}