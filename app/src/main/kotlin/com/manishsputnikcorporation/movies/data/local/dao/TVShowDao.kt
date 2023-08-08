package com.manishsputnikcorporation.movies.data.local.dao

import android.database.sqlite.SQLiteException
import androidx.room.*
import com.manishsputnikcorporation.movies.data.local.DatabaseTVShow
import com.manishsputnikcorporation.movies.utils.enums.Type
import java.io.IOException
import kotlinx.coroutines.flow.Flow

@Dao
interface TVShowDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
  suspend fun insert(movie: DatabaseTVShow)

  @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(tvShows: List<DatabaseTVShow>)

  @Update suspend fun update(tvShow: DatabaseTVShow)

  @Delete suspend fun delete(tvShow: DatabaseTVShow)

  @Query("SELECT * FROM tvShow WHERE id = :id") fun getTVShow(id: String): Flow<DatabaseTVShow>

  @Query("SELECT is_favorite FROM movie WHERE id = :id")
  fun isFavoriteTVShow(id: String): Flow<Boolean>

  @Query("SELECT * FROM movie ORDER BY rank ASC")
  fun getTVShows(): Flow<List<DatabaseTVShow>>

  @Query("SELECT * FROM movie WHERE is_favorite = true ORDER BY rank ASC")
  fun getFavorites(): Flow<List<DatabaseTVShow>>

  // region Helper methods
  // fun getDistinctMovieById(id: String) = getMovie(id).distinctUntilChanged()

  // fun getDistinctMovies(type: Type) = getMovies(type).distinctUntilChanged()
  // endregion
}
