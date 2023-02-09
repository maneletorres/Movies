package com.manishsputnikcorporation.movies.di

import android.content.Context
import com.manishsputnikcorporation.movies.BuildConfig
import com.manishsputnikcorporation.movies.data.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.data.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.data.MovieRemoteDataSourceImpl
import com.manishsputnikcorporation.movies.data.MovieRepositoryImpl
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapperImpl
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.MovieRepository
import com.manishsputnikcorporation.movies.network.CustomServicesExecutor
import com.manishsputnikcorporation.movies.network.MoviesService
import com.manishsputnikcorporation.movies.network.NetworkManager
import com.manishsputnikcorporation.movies.network.NetworkManagerImpl
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.network.util.API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class) // TODO: to unify with AppModule
object AppModule2 {

  @Provides fun provideBaseUrl() = API_BASE_URL

  @Singleton
  @Provides
  fun provideOkHttpClient() =
      if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
      } else {
        OkHttpClient.Builder().build()
      }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
      Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BASE_URL)
          .client(okHttpClient)
          .build()

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): MoviesService =
      retrofit.create(MoviesService::class.java)

  @Provides
  @Singleton
  fun bindNetworkManagerProvider(@ApplicationContext context: Context): NetworkManager =
      NetworkManagerImpl(context)

  @Provides fun bindGlobalErrorMapperProvider(): GlobalErrorMapper = GlobalErrorMapperImpl()

  @Provides
  fun bindMovieDataSourceProvider(
      customService: CustomServicesExecutor.Default,
      service: MoviesService
  ): MovieRemoteDataSource = MovieRemoteDataSourceImpl(customService, service)

  @Provides
  fun bindMovieRepositoryProvider(
      movieRemoteDataSource: MovieRemoteDataSource,
      movieDtoMapper: MovieDtoMapper,
      imdbMovieDtoMapper: MovieDtoMapper
  ): MovieRepository = MovieRepositoryImpl(movieRemoteDataSource, movieDtoMapper)

  @Provides
  fun bindMovieDetailRepositoryProvider(
      movieRemoteDataSource: MovieRemoteDataSource,
      imdbMovieDtoMapper: MovieDetailDtoMapper
  ): MovieDetailRepository = MovieDetailRepositoryImpl(movieRemoteDataSource, imdbMovieDtoMapper)

  @Provides @Singleton fun bindMovieDtoMapperProvider(): MovieDtoMapper = MovieDtoMapper()

  @Provides
  @Singleton
  fun bindMovieDetailDtoMapperProvider(): MovieDetailDtoMapper = MovieDetailDtoMapper()
}
