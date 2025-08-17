package com.manishsputnikcorporation.movies.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.dao.TVDao
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTV
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTVs
import java.io.IOException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvDaoTest {

    // region Setup
    private lateinit var db: MediaDatabase
    private lateinit var tvDao: TVDao

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context = context, klass = MediaDatabase::class.java).allowMainThreadQueries().build()
        tvDao = db.tvDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    // endregion

    // region Testing
    @Test
    @Throws(Exception::class)
    fun insertTV() = runTest {
        tvDao.insert(tv = fakeDatabaseTV)

        tvDao.getTV(id = fakeDatabaseTV.id).test {
            val databaseTV = awaitItem()
            assertNotNull(databaseTV)
            assertEquals(fakeDatabaseTV, databaseTV)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAllTVs() = runTest {
        tvDao.insertAll(tvs = fakeDatabaseTVs)

        tvDao.getTVs().test {
            val databaseTVs = awaitItem()
            assert(databaseTVs.isNotEmpty())
            assert(databaseTVs.containsAll(elements = fakeDatabaseTVs))
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateTV() = runTest {
        tvDao.insert(fakeDatabaseTV)
        val updatedFakeTV = fakeDatabaseTV.copy(name = "updated-TV-name")
        tvDao.update(updatedFakeTV)

        tvDao.getTV(id = fakeDatabaseTV.id).test { assert(awaitItem().name == updatedFakeTV.name) }
    }

    @Test
    @Throws(Exception::class)
    fun deleteTV() = runTest {
        tvDao.insertAll(fakeDatabaseTVs)
        tvDao.delete(tv = fakeDatabaseTV)

        tvDao.getTVs().test {
            val databaseTVs = awaitItem()
            assertNotNull(databaseTVs)
            assertTrue(databaseTVs.isNotEmpty())
            assertFalse(databaseTVs.contains(fakeDatabaseTV))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getTV() = runTest {
        tvDao.insert(fakeDatabaseTV)

        tvDao.getTV(id = fakeDatabaseTV.id).test {
            val databaseTV = awaitItem()
            assertNotNull(databaseTV)
            assertEquals(fakeDatabaseTV, databaseTV)
        }
    }

    @Test
    @Throws(Exception::class)
    fun isFavoriteTV() = runTest {
        tvDao.insert(fakeDatabaseTV)

        tvDao.isFavoriteTV(id = fakeDatabaseTV.id).test { assertEquals(fakeDatabaseTV.isFavorite, awaitItem()) }
    }

    @Test
    @Throws(Exception::class)
    fun isNotFavoriteTV() = runTest {
        tvDao.insert(fakeDatabaseTV.copy(isFavorite = false))

        tvDao.isFavoriteTV(id = fakeDatabaseTV.id).test { assertFalse(awaitItem()) }
    }

    @Test
    @Throws(Exception::class)
    fun getTVs() = runTest {
        tvDao.insertAll(tvs = fakeDatabaseTVs)

        tvDao.getTVs().test {
            val tvs = awaitItem()
            assert(tvs.isNotEmpty())
            assert(tvs.containsAll(elements = fakeDatabaseTVs))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getFavorites() = runTest {
        tvDao.insertAll(tvs = fakeDatabaseTVs)

        tvDao.getFavorites().test {
            val favorites = awaitItem()
            assert(favorites.isNotEmpty())
            assert(favorites.containsAll(elements = fakeDatabaseTVs.filter { it.isFavorite }))
            assertFalse(favorites.containsAll(elements = fakeDatabaseTVs.filterNot { it.isFavorite }))
        }
    }
    // endregion
}
