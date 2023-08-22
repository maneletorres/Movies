package com.manishsputnikcorporation.movies.network.service

import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowDetailDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

  @GET("movie/popular")
  fun getPopularMovies(
      @Query("api_key") apiKey: String,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int = 1
  ): Call<MoviesDto>

  @GET("tv/popular")
  fun getPopularTVShows(
      @Query("api_key") apiKey: String,
      @Query("language") lang: String = "en-US",
      @Query("page") page: Int = 1
  ): Call<TVShowsDto>

  @GET("movie/{id}")
  fun getMovie(@Path("id") id: String, @Query("api_key") apiKey: String): Call<MovieDetailDto>

  @GET("tv/{id}")
  fun getTVShow(@Path("id") id: String, @Query("api_key") apiKey: String): Call<TVShowDetailDto>
}
