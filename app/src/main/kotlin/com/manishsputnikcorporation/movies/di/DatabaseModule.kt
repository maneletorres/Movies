package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.room.Room
import com.manishsputnikcorporation.movies.data.local.MovieDao
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @ApplicationScope
  @Provides
  @Singleton
  fun provideApplicationScope() = CoroutineScope(SupervisorJob())

  @Provides
  @Singleton
  fun provideRoomDatabase(
      @ApplicationContext context: Context,
      callback: MovieRoomDatabase.Callback
  ): MovieRoomDatabase =
      Room.databaseBuilder(context, MovieRoomDatabase::class.java, "movie_database")
          .fallbackToDestructiveMigration()
          /** Database preload (only for debug purposes): */
          //.addCallback(callback)
          .build()

  @Provides
  fun provideMovieDao(movieRoomDatabase: MovieRoomDatabase): MovieDao = movieRoomDatabase.movieDao()
}

@Retention @Qualifier annotation class ApplicationScope
