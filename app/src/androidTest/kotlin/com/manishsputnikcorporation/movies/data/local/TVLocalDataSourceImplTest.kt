package com.manishsputnikcorporation.movies.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.dao.TVDao
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSourceImpl
import com.manishsputnikcorporation.movies.di.annotations.TestDatabase
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTV
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTVs
import dagger.hilt.android.testing.HiltAndroidTest
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TVLocalDataSourceImplTest {

    // region Setup
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    @Inject @TestDatabase lateinit var db: MediaDatabase
    private lateinit var tvDao: TVDao
    private lateinit var localDataSource: TVLocalDataSource

    @Before
    fun setup() {
        db =
            Room.inMemoryDatabaseBuilder(context = ApplicationProvider.getApplicationContext(), klass = MediaDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        tvDao = db.tvDao()
        localDataSource = TVLocalDataSourceImpl(tvDao)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    // endregion

    // region Testing
    @Test
    @Throws(Exception::class)
    fun insertAll() = runTest {
        localDataSource.insertAll(tvs = fakeDatabaseTVs)

        tvDao.getTVs().test {
            val databaseTVs: List<DatabaseTV> = awaitItem()
            assertTrue(databaseTVs.isNotEmpty())
            assertTrue(databaseTVs.containsAll(elements = fakeDatabaseTVs))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getTVs() = runTest {
        tvDao.insertAll(tvs = fakeDatabaseTVs)

        localDataSource.getTVs().test {
            val databaseTVs: List<DatabaseTV> = awaitItem()
            assertTrue(databaseTVs.isNotEmpty())
            assertTrue(databaseTVs.containsAll(elements = fakeDatabaseTVs))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getFavoriteTVs() = runTest {
        tvDao.insertAll(tvs = fakeDatabaseTVs.map { it.copy(isFavorite = true) })

        localDataSource.getFavoriteTVs().test {
            val favoriteDatabaseTVs: List<DatabaseTV> = awaitItem()
            assertTrue(favoriteDatabaseTVs.isNotEmpty())
            assertTrue(favoriteDatabaseTVs.containsAll(elements = fakeDatabaseTVs.filter { it.isFavorite }))
        }
    }

    @Test
    @Throws(Exception::class)
    fun isFavoriteTV() = runTest {
        tvDao.insert(tv = fakeDatabaseTV.copy(isFavorite = true))

        localDataSource.isFavoriteTV(id = fakeDatabaseTV.id).test { assertTrue(awaitItem()) }
    }

    @Test
    @Throws(Exception::class)
    fun getTV() = runTest {
        tvDao.insert(tv = fakeDatabaseTV)

        localDataSource.getTV(id = fakeDatabaseTV.id).test {
            val databaseTV: DatabaseTV = awaitItem()
            assertNotNull(databaseTV)
            assertEquals(fakeDatabaseTV, databaseTV)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateTV() = runTest {
        tvDao.insert(tv = fakeDatabaseTV)

        val updatedDatabaseTV = fakeDatabaseTV.copy(name = "updated-tv-title")

        localDataSource.updateTV(tv = updatedDatabaseTV)

        tvDao.getTV(id = updatedDatabaseTV.id).test {
            val databaseTV: DatabaseTV = awaitItem()
            assertNotNull(databaseTV)
            assertEquals(updatedDatabaseTV.name, databaseTV.name)
        }
    }
    // endregion
}
