package com.manishsputnikcorporation.movies.data.local

import android.database.sqlite.SQLiteException
import androidx.room.*
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
  suspend fun insert(movie: DatabaseMovie)

  @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(movies: List<DatabaseMovie>)

  @Update suspend fun update(movie: DatabaseMovie)

  @Delete suspend fun delete(movie: DatabaseMovie)

  @Query("SELECT * FROM movie WHERE id = :id") fun getMovie(id: String): Flow<DatabaseMovie>

  @Query("SELECT * FROM movie ORDER BY title ASC") fun getMovies(): Flow<List<DatabaseMovie>>

  // region Helper methods
  fun getDistinctMovieById(id: String) = getMovie(id).distinctUntilChanged()
  fun getDistinctMovies() = getMovies().distinctUntilChanged()
  // endregion
}
