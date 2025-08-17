package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.room.Room
import com.manishsputnikcorporation.movies.data.local.MediaDatabase
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.dao.TVDao
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

    @ApplicationScope @Provides @Singleton fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context, callback: MediaDatabase.Callback): MediaDatabase =
        Room.databaseBuilder(context = context, klass = MediaDatabase::class.java, name = "movie_database")
            .fallbackToDestructiveMigration(dropAllTables = false)
            /** Pre-populate the database (only for test purposes): */
            // .addCallback(callback)
            .build()

    @Provides fun provideMovieDao(mediaDatabase: MediaDatabase): MovieDao = mediaDatabase.movieDao()

    @Provides fun provideTvDao(mediaDatabase: MediaDatabase): TVDao = mediaDatabase.tvDao()
}

@Retention @Qualifier annotation class ApplicationScope
