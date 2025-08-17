package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    // TODO: optimize the loadData and getData calls to this!!
    // val movies: Flow<List<MovieModel>>
    // val tvs: Flow<List<TVModel>>

    // region Movie
    fun refreshPopularMovies(): DomainResponse<Unit>

    fun getMovies(): Flow<List<MovieModel>>

    fun getFavoriteMovies(): Flow<List<MovieModel>>

    // endregion

    // region TV
    fun refreshPopularTVs(): DomainResponse<Unit>

    fun getTVs(): Flow<List<TVModel>>

    fun getFavoriteTVs(): Flow<List<TVModel>>

    // endregion
}
