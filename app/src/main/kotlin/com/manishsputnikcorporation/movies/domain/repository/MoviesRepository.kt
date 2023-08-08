package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.model.tvshow.TVShowModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

  val movies: Flow<List<MovieModel>>
  val tvShows: Flow<List<TVShowModel>>

  fun refreshPopularMovies(): DomainResponse<Unit>

  fun refreshPopularTVShows(): DomainResponse<Unit>

  fun getMovies(): Flow<List<MovieModel>>

  fun getTVShows(): Flow<List<TVShowModel>>

  fun getFavoriteMovies(): Flow<List<MovieModel>>

  fun getFavoriteTVShows(): Flow<List<TVShowModel>>
}
