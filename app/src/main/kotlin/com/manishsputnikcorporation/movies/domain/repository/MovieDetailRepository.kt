package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovieDetail(id: Int): DomainResponse<MovieDetailModel>

    fun isFavoriteMovie(id: Int): Flow<Boolean>

    fun getMovie(id: Int): Flow<MovieModel>

    suspend fun updateMovie(id: Int): DomainResponse<Boolean>
}
