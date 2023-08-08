package com.manishsputnikcorporation.movies.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.dao.TVShowDao
import com.manishsputnikcorporation.movies.di.ApplicationScope
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.asDatabaseMovieModel
import com.manishsputnikcorporation.movies.network.asDatabaseTVShowModel
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DatabaseMovie::class, DatabaseTVShow::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

  abstract fun movieDao(): MovieDao
  abstract fun tvShowDao(): TVShowDao

  class Callback
  @Inject
  constructor(
      private val database: Provider<MovieRoomDatabase>,
      @ApplicationScope private val applicationScope: CoroutineScope
  ) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)

      with (database.get()) {
        applicationScope.launch {
          movieDao().insertAll(fakeMovieListDto.asDatabaseMovieModel())
          tvShowDao().insertAll(fakeSeriesListDto.asDatabaseTVShowModel())
        }
      }
    }
  }
}
