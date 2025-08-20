package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.room.Room
import com.manishsputnikcorporation.movies.data.local.MediaDatabase
import com.manishsputnikcorporation.movies.di.annotations.TestDatabase
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.network.model.TVDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.TVDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @TestDatabase
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, klass = MediaDatabase::class.java).allowMainThreadQueries().build()
}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [MapperModule::class])
object FakeMapperModule {

    @Provides fun provideMovieDtoMapper() = MovieDtoMapper()

    @Provides fun provideMovieDetailDtoMapper() = MovieDetailDtoMapper()

    @Provides fun provideTVDtoMapper() = TVDtoMapper()

    @Provides fun provideTVDetailDtoMapper() = TVDetailDtoMapper()
}
