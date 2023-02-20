package com.manishsputnikcorporation.movies.network

import com.manishsputnikcorporation.movies.network.response.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.MoviesDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

  @GET("MostPopularMovies/{api_key}")
  fun getMostPopularMovies(
      @Path("api_key") apiKey: String,
      @Query("lang") lang: String = "en"
  ): Call<MoviesDto>

  @GET("MostPopularTVs/{api_key}")
  fun getMostPopularTVs(
      @Path("api_key") apiKey: String,
      @Query("lang") lang: String = "en"
  ): Call<MoviesDto>

  @GET("Title/{api_key}/{id}")
  fun getMovie(
      @Path("api_key") apiKey: String,
      @Path("id") id: String,
      @Query("lang") lang: String = "en"
  ): Call<MovieDetailDto>
}
