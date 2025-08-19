package com.manishsputnikcorporation.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.dao.TVDao
import com.manishsputnikcorporation.movies.di.ApplicationScope
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVsDto
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DatabaseMovie::class, DatabaseTV::class], version = 1, exportSchema = false)
abstract class MediaDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvDao(): TVDao

    class Callback
    @Inject
    constructor(private val database: Provider<MediaDatabase>, @param:ApplicationScope private val applicationScope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {
                with(receiver = database.get()) {
                    movieDao().insertAll(movies = fakeMoviesDto.asDatabaseModel())
                    tvDao().insertAll(tvs = fakeTVsDto.asDatabaseModel())
                }
            }
        }
    }
}
