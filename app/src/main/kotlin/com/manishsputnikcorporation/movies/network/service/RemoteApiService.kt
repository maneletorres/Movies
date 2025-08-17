package com.manishsputnikcorporation.movies.network.service

import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService {

    @GET("movie/popular") fun getPopularMovies(@Query("language") lang: String = "en-US", @Query("page") page: Int = 1): Call<MoviesDto>

    @GET("tv/popular") fun getPopularTVs(@Query("language") lang: String = "en-US", @Query("page") page: Int = 1): Call<TVsDto>

    @GET("movie/{id}") fun getMovie(@Path("id") id: Int): Call<MovieDetailDto>

    @GET("tv/{id}") fun getTV(@Path("id") id: Int): Call<TVDetailDto>
}
