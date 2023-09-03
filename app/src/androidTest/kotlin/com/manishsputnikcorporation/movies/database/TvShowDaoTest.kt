package com.manishsputnikcorporation.movies.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manishsputnikcorporation.movies.data.local.DatabaseTvShow
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import com.manishsputnikcorporation.movies.data.local.dao.TvShowDao
import com.manishsputnikcorporation.movies.utils.createTvShow
import java.io.IOException
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowDaoTest {

    // region Setup
    private lateinit var tvShowDao: TvShowDao
    private lateinit var db: MovieRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, MovieRoomDatabase::class.java).allowMainThreadQueries().build()
        tvShowDao = db.tvShowDao()
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
    fun insertTvShow() = runBlocking {
        // Given:
        val fakeTvShow = createTvShow()

        // When:
        tvShowDao.insert(fakeTvShow)

        // Then:
        val tvShowList = tvShowDao.getTvShows().first()
        val tvShow = tvShowList[0]
        TestCase.assertTrue(tvShowList.size == 1)
        MatcherAssert.assertThat(fakeTvShow, CoreMatchers.equalTo(tvShow))

        // TODO: is this necessary?
        TestCase.assertEquals(tvShow.id, fakeTvShow.id)
        TestCase.assertEquals(tvShow.rank, fakeTvShow.rank)
        TestCase.assertEquals(tvShow.title, fakeTvShow.title)
        TestCase.assertEquals(tvShow.year, fakeTvShow.year)
        TestCase.assertEquals(tvShow.imageUrl, fakeTvShow.imageUrl)
        TestCase.assertEquals(tvShow.rating, fakeTvShow.rating)
    }

    @Test
    @Throws(Exception::class)
    fun insertAllTvShowList() = runBlocking {
        // Given:
        val fakeTvShowList = mutableListOf<DatabaseTvShow>()
        for (i in 0..2) fakeTvShowList.add(createTvShow(i.toString()))

        // When:
        tvShowDao.insertAll(fakeTvShowList)

        // Then:
        val tvShowList = tvShowDao.getTvShows().first()
        assert(tvShowList.isNotEmpty())
        assert(tvShowList.containsAll(fakeTvShowList))
    }

    @Test
    @Throws(Exception::class)
    fun updateTvShow() = runBlocking {
        // Given:
        val fakeTvShow = createTvShow()
        val updatedFakeTvShowTitle = "updated-fake-tv-show-title"
        val updatedFakeTvShow = fakeTvShow.copy(title = updatedFakeTvShowTitle)
        tvShowDao.insert(fakeTvShow)

        // When:
        tvShowDao.update(updatedFakeTvShow)

        // Then:
        val tvShow = tvShowDao.getTvShow(fakeTvShow.id).first()
        assert(tvShow.title == updatedFakeTvShowTitle)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTvShow() = runBlocking {
        // Given:
        val fakeTvShow1 = createTvShow("1")
        val fakeTvShow2 = createTvShow("2")
        tvShowDao.insertAll(listOf(fakeTvShow1, fakeTvShow2))

        // When:
        tvShowDao.delete(fakeTvShow1)

        // Then:
        val tvShowList = tvShowDao.getTvShows().first()
        assert(tvShowList.isNotEmpty())
        TestCase.assertFalse(tvShowList.contains(fakeTvShow1))
    }

    @Test
    @Throws(Exception::class)
    fun getTvShow() = runBlocking {
        // Given:
        val fakeTvShow = createTvShow()
        tvShowDao.insert(fakeTvShow)

        // When:
        val tvShow = tvShowDao.getTvShow(fakeTvShow.id).first()

        // Then:
        TestCase.assertNotNull(tvShow)
        MatcherAssert.assertThat(fakeTvShow, CoreMatchers.equalTo(tvShow))
    }

    @Test
    @Throws(Exception::class)
    fun getTvShowList() = runBlocking {
        // Given:
        val fakeTvShowList = listOf(createTvShow("1"), createTvShow("2"))
        tvShowDao.insertAll(fakeTvShowList)

        // When:
        val tvShowList = tvShowDao.getTvShows().first()

        // Then:
        assert(tvShowList.isNotEmpty())
        assert(tvShowList.containsAll(fakeTvShowList))
    }

    @Test
    @Throws(Exception::class)
    fun getFavorites() = runBlocking {
        // Given:
        val fakeNotFavoriteTvShow = createTvShow("1")
        val fakeDataList =
            listOf(
                fakeNotFavoriteTvShow,
                createTvShow("3", true),
            )
        tvShowDao.insertAll(fakeDataList)

        // When:
        val favorites = tvShowDao.getFavorites().first()

        // Then:
        assert(favorites.isNotEmpty())
        TestCase.assertFalse(favorites.contains(fakeNotFavoriteTvShow))
    }
    // endregion
}
