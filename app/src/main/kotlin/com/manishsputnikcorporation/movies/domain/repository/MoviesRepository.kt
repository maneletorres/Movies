package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    // TODO: optimize the loadData and getData calls to this!!
    // val movies: Flow<List<MovieModel>>

    suspend fun refreshPopularMovies(): DomainResponse<Unit>

    fun getMovies(): Flow<List<MovieModel>>

    fun getFavoriteMovies(): Flow<List<MovieModel>>
}
