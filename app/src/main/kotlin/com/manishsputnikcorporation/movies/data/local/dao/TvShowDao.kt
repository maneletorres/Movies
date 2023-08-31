package com.manishsputnikcorporation.movies.data.local.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.manishsputnikcorporation.movies.data.local.DatabaseTvShow
import java.io.IOException
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
  suspend fun insert(tvShow: DatabaseTvShow)

  @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(tvShows: List<DatabaseTvShow>)

  @Update suspend fun update(tvShow: DatabaseTvShow)

  @Delete suspend fun delete(tvShow: DatabaseTvShow)

  @Query("SELECT * FROM tv_shows WHERE id = :id") fun getTvShow(id: String): Flow<DatabaseTvShow>

  @Query("SELECT is_favorite FROM tv_shows WHERE id = :id")
  fun isFavoriteTvShow(id: String): Flow<Boolean>

  @Query("SELECT * FROM tv_shows ORDER BY rank ASC") fun getTvShows(): Flow<List<DatabaseTvShow>>

  @Query("SELECT * FROM tv_shows WHERE is_favorite = true ORDER BY rank ASC")
  fun getFavorites(): Flow<List<DatabaseTvShow>>

  // region Helper methods
  // fun getDistinctTvShowById(id: String) = getTvShow(id).distinctUntilChanged()

  // fun getDistinctTvShows() = getTvShows(type).distinctUntilChanged()
  // endregion
}
