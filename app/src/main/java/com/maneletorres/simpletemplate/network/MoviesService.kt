package com.maneletorres.simpletemplate.network

import com.maneletorres.simpletemplate.network.response.imdb.ImdbMovieDetailDto
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMovieDto
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MovieDto
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMoviesDto
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MoviesDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    // region TheMovieDB:
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ) : Call<MoviesDto>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ) : Call<MoviesDto>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String? = "en-US",
        @Query("append_to_response") appendToResponse: String? = null
    ): Call<MovieDto>
    // endregion

    // region IMDB:
    @GET("MostPopularMovies/{api_key}")
    fun getMostPopularMovies(
        @Path("api_key") apiKey: String,
        @Query("lang") lang: String = "en"
    ) : Call<ImdbMoviesDto>

    @GET("MostPopularTVs/{api_key}")
    fun getMostPopularTVs(
        @Path("api_key") apiKey: String,
        @Query("lang") lang: String = "en"
    ) : Call<ImdbMoviesDto>

    @GET("Title/{api_key}/{id}")
    fun getMovie(
        @Path("api_key") apiKey: String,
        @Path("id") id: String,
        @Query("lang") lang: String = "en"
    ) : Call<ImdbMovieDetailDto>
    // endregion
}