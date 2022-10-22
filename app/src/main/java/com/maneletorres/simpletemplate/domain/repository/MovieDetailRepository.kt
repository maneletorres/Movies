package com.maneletorres.simpletemplate.domain.repository

import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.IMDBMovieDetailModel

interface MovieDetailRepository {

    // TheMovieDB:
    // TODO:

    // IMDB:
    fun getMovie(id: String): DomainResponse<IMDBMovieDetailModel>
}