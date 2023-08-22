package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.TvShowDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
  // region Movie
  fun getMovieDetail(id: String): DomainResponse<MovieDetailModel>

  fun isFavoriteMovie(id: String): Flow<Boolean>

  fun getMovie(id: String): Flow<MediaItemModel>

  suspend fun updateMovie(id: String): DomainResponse<Boolean>
  // endregion

  // region TVShow
  fun getTvShowDetail(id: String): DomainResponse<TvShowDetailModel>

  fun isFavoriteTvShow(id: String): Flow<Boolean>

  fun getTvShow(id: String): Flow<MediaItemModel>

  suspend fun updateTvShow(id: String): DomainResponse<Boolean>
  // endregion
}
