package com.manishsputnikcorporation.movies.data.local

import android.database.sqlite.SQLiteException
import androidx.room.*
import com.manishsputnikcorporation.movies.utils.enums.Type
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

  @Query("SELECT * FROM movie WHERE type = :type ORDER BY rank ASC")
  fun getMovies(type: Type): Flow<List<DatabaseMovie>>

  // region Helper methods
  fun getDistinctMovieById(id: String) = getMovie(id).distinctUntilChanged()
  fun getDistinctMovies(type: Type) = getMovies(type).distinctUntilChanged()
  // endregion
}
