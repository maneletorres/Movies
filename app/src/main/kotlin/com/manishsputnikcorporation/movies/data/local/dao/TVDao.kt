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
interface TVDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO: to find the best strategy
    suspend fun insert(tv: DatabaseTV)

    @Throws(SQLiteException::class, IOException::class) // TODO: is it necessary?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tvs: List<DatabaseTV>)

    @Update suspend fun update(tv: DatabaseTV)

    @Delete suspend fun delete(tv: DatabaseTV)

    @Query("UPDATE tvs SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM tvs WHERE id = :id") fun getTV(id: Int): Flow<DatabaseTV>

    @Query("SELECT is_favorite FROM tvs WHERE id = :id") fun isFavoriteTV(id: Int): Flow<Boolean>

    @Query("SELECT * FROM tvs ORDER BY rating ASC") fun getTVs(): Flow<List<DatabaseTV>>

    @Query("SELECT * FROM tvs WHERE is_favorite = true ORDER BY rating ASC") fun getFavorites(): Flow<List<DatabaseTV>>

    // region Helper methods
    // fun getDistinctTVById(id: String) = getTV(id).distinctUntilChanged()

    // fun getDistinctTVs() = getTVs(type).distinctUntilChanged()
    // endregion
}