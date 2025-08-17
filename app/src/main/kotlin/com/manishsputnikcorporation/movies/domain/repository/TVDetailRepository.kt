package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.TvDetailModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    // region Movie
    fun getMovieDetail(id: String): DomainResponse<MovieDetailModel>

    fun isFavoriteMovie(id: String): Flow<Boolean>

    fun getMovie(id: String): Flow<MovieModel>

    suspend fun updateMovie(id: String): DomainResponse<Boolean>

    // endregion

    // region TV
    fun getTVDetail(id: String): DomainResponse<TvDetailModel>

    fun isFavoriteTV(id: String): Flow<Boolean>

    fun getTVById(id: String): Flow<TVModel>

    suspend fun updateTV(id: String): DomainResponse<Boolean>
    // endregion
}
