package com.manishsputnikcorporation.movies.data.local.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.manishsputnikcorporation.movies.data.local.DatabaseTV
import java.io.IOException
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
    suspend fun insert(tvShow: DatabaseTV)

    @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tvShows: List<DatabaseTV>)

    @Update suspend fun update(tvShow: DatabaseTV)

    @Delete suspend fun delete(tvShow: DatabaseTV)

    @Query("SELECT * FROM tvs WHERE id = :id") fun getTvShow(id: String): Flow<DatabaseTV>

    @Query("SELECT is_favorite FROM tvs WHERE id = :id") fun isFavoriteTvShow(id: String): Flow<Boolean>

    @Query("SELECT * FROM tvs ORDER BY rating ASC") fun getTvShows(): Flow<List<DatabaseTV>>

    @Query("SELECT * FROM tvs WHERE is_favorite = true ORDER BY rating ASC") fun getFavorites(): Flow<List<DatabaseTV>>

    // region Helper methods
    // fun getDistinctTvShowById(id: String) = getTvShow(id).distinctUntilChanged()

    // fun getDistinctTvShows() = getTvShows(type).distinctUntilChanged()
    // endregion
}
