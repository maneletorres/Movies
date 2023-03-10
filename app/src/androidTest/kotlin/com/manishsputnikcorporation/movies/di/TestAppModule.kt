package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.room.Room
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import com.manishsputnikcorporation.movies.di.annotations.ApiUrl
import com.manishsputnikcorporation.movies.di.annotations.TestDatabase
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.network.util.TEST_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

  @Provides
  @TestDatabase
  fun provideInMemoryDb(@ApplicationContext context: Context) =
      Room.inMemoryDatabaseBuilder(context, MovieRoomDatabase::class.java)
          .allowMainThreadQueries()
          .build()
}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ApiUrlModule::class])
object FakeApiBaseUrlModule {

  @Provides @Singleton @ApiUrl fun provideBaseUrl() = TEST_API_BASE_URL
}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [MapperModule::class])
object FakeMapperModule {

  @Provides fun provideMovieDtoMapper() = MovieDtoMapper()

  @Provides fun provideMovieDetailDtoMapper() = MovieDetailDtoMapper()
}
