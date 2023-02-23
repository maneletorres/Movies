package com.manishsputnikcorporation.movies.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manishsputnikcorporation.movies.di.ApplicationScope
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DatabaseMovie::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

  abstract fun movieDao(): MovieDao

  class Callback
  @Inject
  constructor(
      private val database: Provider<MovieRoomDatabase>,
      @ApplicationScope private val applicationScope: CoroutineScope
  ) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)

      val dao = database.get().movieDao()
      applicationScope.launch {
        dao.insertAll(fakeMoviesDto.asDatabaseModel())
      }
    }
  }
}
