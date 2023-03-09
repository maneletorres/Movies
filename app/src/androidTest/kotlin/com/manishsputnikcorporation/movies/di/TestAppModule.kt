package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.room.Room
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import com.manishsputnikcorporation.movies.di.annotations.TestDatabase
import com.manishsputnikcorporation.movies.di.annotations.TestMovieDetailMapper
import com.manishsputnikcorporation.movies.di.annotations.TestMovieMapper
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

  @Provides
  @TestDatabase
  fun provideInMemoryDb(@ApplicationContext context: Context) =
      Room.inMemoryDatabaseBuilder(context, MovieRoomDatabase::class.java)
          .allowMainThreadQueries()
          .build()

  @Provides @TestMovieMapper fun provideTestMovieDtoMapper() = MovieDtoMapper()

  @Provides @TestMovieDetailMapper fun provideTestMovieDetailDtoMapper() = MovieDetailDtoMapper()
}
