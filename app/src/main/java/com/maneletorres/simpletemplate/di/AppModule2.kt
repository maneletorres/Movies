package com.maneletorres.simpletemplate.di

import android.content.Context
import com.maneletorres.simpletemplate.BuildConfig
import com.maneletorres.simpletemplate.data.MovieDetailRepositoryImpl
import com.maneletorres.simpletemplate.data.MovieRemoteDataSource
import com.maneletorres.simpletemplate.data.MovieRemoteDataSourceImpl
import com.maneletorres.simpletemplate.data.MovieRepositoryImpl
import com.maneletorres.simpletemplate.domain.error.GlobalErrorMapper
import com.maneletorres.simpletemplate.domain.error.GlobalErrorMapperImpl
import com.maneletorres.simpletemplate.domain.repository.MovieDetailRepository
import com.maneletorres.simpletemplate.domain.repository.MovieRepository
import com.maneletorres.simpletemplate.network.*
import com.maneletorres.simpletemplate.network.model.IMDBMovieDetailDtoMapper
import com.maneletorres.simpletemplate.network.model.IMDBMovieDtoMapper
import com.maneletorres.simpletemplate.network.model.MovieDtoMapper
import com.maneletorres.simpletemplate.network.util.IMDB_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // TODO: to unify with AppModule
object AppModule2 {

    @Provides
    fun provideBaseUrl() = IMDB_API_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(MoviesService::class.java)

    @Provides
    @Singleton
    fun bindNetworkManagerProvider(@ApplicationContext context: Context): NetworkManager = NetworkManagerImpl(context)

    @Provides
    fun bindGlobalErrorMapperProvider(): GlobalErrorMapper = GlobalErrorMapperImpl()

    @Provides
    fun bindMovieDataSourceProvider(customService: CustomServicesExecutor.Default, service: MoviesService): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(customService, service)

    @Provides
    fun bindMovieRepositoryProvider(movieRemoteDataSource: MovieRemoteDataSource, movieDtoMapper: MovieDtoMapper, imdbMovieDtoMapper: IMDBMovieDtoMapper): MovieRepository =
        MovieRepositoryImpl(movieRemoteDataSource, movieDtoMapper, imdbMovieDtoMapper)

    @Provides
    fun bindMovieDetailRepositoryProvider(movieRemoteDataSource: MovieRemoteDataSource, imdbMovieDtoMapper: IMDBMovieDetailDtoMapper): MovieDetailRepository =
        MovieDetailRepositoryImpl(movieRemoteDataSource, imdbMovieDtoMapper)

    @Provides
    @Singleton
    fun bindMovieDtoMapperProvider(): MovieDtoMapper = MovieDtoMapper()

    @Provides
    @Singleton
    fun bindIMBDMovieDtoMapperProvider(): IMDBMovieDtoMapper = IMDBMovieDtoMapper()

    @Provides
    @Singleton
    fun bindIMBDMovieDetailDtoMapperProvider(): IMDBMovieDetailDtoMapper = IMDBMovieDetailDtoMapper()
}