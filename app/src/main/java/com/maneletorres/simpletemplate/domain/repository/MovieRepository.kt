package com.maneletorres.simpletemplate.domain.repository

import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.IMDBMovieDetailModel
import com.maneletorres.simpletemplate.domain.model.IMDBMovieModel
import com.maneletorres.simpletemplate.domain.model.MovieModel

interface MovieRepository {

    // TheMovieDB:
    fun getPopularMovies(): DomainResponse<List<MovieModel>>

    fun getTopRatedMovies(): DomainResponse<List<MovieModel>>

    fun getMovie(movieId: Int): DomainResponse<MovieModel>

    // IMDB:
    fun getMostPopularMovies(): DomainResponse<List<IMDBMovieModel>>

    fun getMostPopularTVs(): DomainResponse<List<IMDBMovieModel>>
}