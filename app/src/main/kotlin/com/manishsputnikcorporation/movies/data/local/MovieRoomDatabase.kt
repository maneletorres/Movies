package com.manishsputnikcorporation.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.dao.TvShowDao
import com.manishsputnikcorporation.movies.di.ApplicationScope
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTvShowsDto
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DatabaseMovie::class, DatabaseTvShow::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao

    class Callback
    @Inject
    constructor(private val database: Provider<MovieRoomDatabase>, @ApplicationScope private val applicationScope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {
                with(database.get()) {
                    movieDao().insertAll(fakeMoviesDto.asDatabaseModel())
                    tvShowDao().insertAll(fakeTvShowsDto.asDatabaseModel())
                }
            }
        }
    }
}
