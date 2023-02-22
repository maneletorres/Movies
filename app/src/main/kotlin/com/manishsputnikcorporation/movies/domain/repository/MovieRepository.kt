package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import retrofit2.http.GET

interface MovieRepository {

  @GET("mostPopularMovies")
  suspend fun getMostPopularMovies(): DomainResponse<List<MovieModel>>

  @GET("mostPopularTVs")
  suspend fun getMostPopularTVs(): DomainResponse<List<MovieModel>>
}
