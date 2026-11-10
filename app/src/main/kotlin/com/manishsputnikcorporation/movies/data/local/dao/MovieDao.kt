package com.manishsputnikcorporation.movies.data.local.dao

import android.database.sqlite.SQLiteException
import androidx.room.*
import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import java.io.IOException
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
    suspend fun insert(movie: DatabaseMovie)

    @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<DatabaseMovie>)

    @Update suspend fun update(movie: DatabaseMovie)

    @Delete suspend fun delete(movie: DatabaseMovie)

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE id = :id") suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM movies WHERE id = :id") fun getMovie(id: Int): Flow<DatabaseMovie>

    @Query("SELECT is_favorite FROM movies WHERE id = :id") fun isFavoriteMovie(id: Int): Flow<Boolean>

    @Query("SELECT * FROM movies ORDER BY rating ASC") fun getMovies(): Flow<List<DatabaseMovie>>

    @Query("SELECT * FROM movies WHERE is_favorite = true ORDER BY rating ASC") fun getFavorites(): Flow<List<DatabaseMovie>>

    // region Helper methods
    // fun getDistinctMovieById(id: Int) = getMovie(id).distinctUntilChanged()

    // fun getDistinctMovies() = getMovies(type).distinctUntilChanged()
    // endregion
}
