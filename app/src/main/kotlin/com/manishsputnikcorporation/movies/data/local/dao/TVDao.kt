package com.manishsputnikcorporation.movies.data.local.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.manishsputnikcorporation.movies.data.local.DatabaseTv
import java.io.IOException
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
    suspend fun insert(tvShow: DatabaseTv)

    @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tvShows: List<DatabaseTv>)

    @Update suspend fun update(tvShow: DatabaseTv)

    @Delete suspend fun delete(tvShow: DatabaseTv)

    @Query("SELECT * FROM tv_shows WHERE id = :id") fun getTvShow(id: String): Flow<DatabaseTv>

    @Query("SELECT is_favorite FROM tv_shows WHERE id = :id") fun isFavoriteTvShow(id: String): Flow<Boolean>

    @Query("SELECT * FROM tv_shows ORDER BY rating ASC") fun getTvShows(): Flow<List<DatabaseTv>>

    @Query("SELECT * FROM tv_shows WHERE is_favorite = true ORDER BY rating ASC") fun getFavorites(): Flow<List<DatabaseTv>>

    // region Helper methods
    // fun getDistinctTvShowById(id: String) = getTvShow(id).distinctUntilChanged()

    // fun getDistinctTvShows() = getTvShows(type).distinctUntilChanged()
    // endregion
}
