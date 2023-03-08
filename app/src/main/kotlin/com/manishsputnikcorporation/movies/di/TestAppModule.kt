package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.room.Room
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

  @Provides
  @Named("test_db")
  fun provideInMemoryDb(@ApplicationContext context: Context) =
      Room.inMemoryDatabaseBuilder(context, MovieRoomDatabase::class.java)
          .allowMainThreadQueries()
          .build()

  @Provides @Named("test_movie_mapper") fun provideTestMovieDtoMapper() = MovieDtoMapper()

  @Provides
  @Named("test_movie_detail_mapper")
  fun provideTestMovieDetailDtoMapper() = MovieDetailDtoMapper()
}
