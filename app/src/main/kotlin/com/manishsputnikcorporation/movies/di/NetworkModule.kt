package com.manishsputnikcorporation.movies.di

import android.content.Context
import com.manishsputnikcorporation.movies.BuildConfig.BEARER_TOKEN
import com.manishsputnikcorporation.movies.BuildConfig.DEBUG
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSourceImpl
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSourceImpl
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSourceImpl
import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSource
import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSourceImpl
import com.manishsputnikcorporation.movies.network.NetworkManager
import com.manishsputnikcorporation.movies.network.NetworkManagerImpl
import com.manishsputnikcorporation.movies.network.service.RemoteApiService
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceExecutor
import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
import com.manishsputnikcorporation.movies.network.util.TMDB_API_BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides @Singleton fun provideBaseUrl() = TMDB_API_BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .apply {
                if (DEBUG) addInterceptor(interceptor = HttpLoggingInterceptor().setLevel(BODY))
                addInterceptor(
                    Interceptor {
                        val newRequest = it.request().newBuilder().addHeader("Authorization", "Bearer $BEARER_TOKEN").build()
                        it.proceed(newRequest)
                    }
                )
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(MoshiConverterFactory.create()).client(okHttpClient).build()

    @Provides @Singleton fun provideMoviesService(retrofit: Retrofit): RemoteApiService = retrofit.create(RemoteApiService::class.java)

    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager = NetworkManagerImpl(context = context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds @Singleton abstract fun bindServiceExecutor(retrofitServicesExecutor: RetrofitServiceExecutor): ServiceExecutor

    @Binds @Singleton abstract fun bindMovieLocalDataSource(movieLocalDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource

    @Binds @Singleton abstract fun bindTvLocalDataSource(movieLocalDataSource: TVLocalDataSourceImpl): TVLocalDataSource

    @Binds @Singleton abstract fun bindMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds @Singleton abstract fun bindTVRemoteDataSource(tvRemoteDataSource: TVRemoteDataSourceImpl): TVRemoteDataSource
}
