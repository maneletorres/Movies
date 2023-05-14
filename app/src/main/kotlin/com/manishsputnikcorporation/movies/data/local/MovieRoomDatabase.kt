package com.manishsputnikcorporation.movies.data.local

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manishsputnikcorporation.movies.di.ApplicationScope
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import dagger.hilt.android.qualifiers.ApplicationContext
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
      @ApplicationContext private val context: Context, // TODO: to check!
      private val database: Provider<MovieRoomDatabase>,
      @ApplicationScope private val applicationScope: CoroutineScope
  ) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)

      val dao = database.get().movieDao()
      applicationScope.launch {
        dao.insertAll(
            fakeMovieListDto(context)
                .asDatabaseModel(MOVIE)
                .plus(fakeSeriesListDto(context).asDatabaseModel(SERIES)))
      }
    }
  }
}
