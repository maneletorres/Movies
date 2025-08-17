package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    // TODO: optimize the loadData and getData calls to this!!
    // val movies: Flow<List<MediaItemModel>>
    // val tvShows: Flow<List<MediaItemModel>>

    // region Movie
    fun refreshPopularMovies(): DomainResponse<Unit>

    fun getMovies(): Flow<List<MediaItemModel>>

    fun getFavoriteMovies(): Flow<List<MediaItemModel>>

    // endregion

    // region TVShow
    fun refreshPopularTVShows(): DomainResponse<Unit>

    fun getTvShows(): Flow<List<MediaItemModel>>

    fun getFavoriteTvShows(): Flow<List<MediaItemModel>>

    // endregion
}
