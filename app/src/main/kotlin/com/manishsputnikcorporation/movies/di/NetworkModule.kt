package com.manishsputnikcorporation.movies.di

import android.content.Context
import com.manishsputnikcorporation.movies.BuildConfig.DEBUG
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSourceImpl
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSourceImpl
import com.manishsputnikcorporation.movies.network.CustomServicesExecutor
import com.manishsputnikcorporation.movies.network.MoviesService
import com.manishsputnikcorporation.movies.network.NetworkManager
import com.manishsputnikcorporation.movies.network.NetworkManagerImpl
import com.manishsputnikcorporation.movies.network.util.API_BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

  @Provides @Singleton fun provideBaseUrl() = API_BASE_URL

  @Provides
  @Singleton
  fun provideOkHttpClient() =
      OkHttpClient.Builder()
          .apply { if (DEBUG) addInterceptor(HttpLoggingInterceptor().setLevel(BODY)) }
          .build()

  @Provides
  @Singleton
  fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
      Retrofit.Builder()
          .baseUrl(baseUrl)
          .addConverterFactory(MoshiConverterFactory.create())
          .client(okHttpClient)
          .build()

  @Provides
  @Singleton
  fun provideMoviesService(retrofit: Retrofit): MoviesService =
      retrofit.create(MoviesService::class.java)

  @Provides
  @Singleton
  fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager =
      NetworkManagerImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

  @Binds
  @Singleton
  abstract fun bindCustomServicesExecutor(
      customServicesExecutor: CustomServicesExecutor.Default
  ): CustomServicesExecutor

  @Binds
  @Singleton
  abstract fun bindMovieLocalDataSource(
      movieLocalDataSource: MovieLocalDataSourceImpl
  ): MovieLocalDataSource

  @Binds
  @Singleton
  abstract fun bindMovieRemoteDataSource(
      movieRemoteDataSource: MovieRemoteDataSourceImpl
  ): MovieRemoteDataSource
}
