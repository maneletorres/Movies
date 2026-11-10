package com.manishsputnikcorporation.movies.domain.repository

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toTVModel
import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSource
import com.manishsputnikcorporation.movies.data.repository.TVsRepositoryImpl
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTVs
import com.manishsputnikcorporation.movies.utils.fake.fakeTVsDto
import com.manishsputnikcorporation.movies.utils.fakeCustomError
import io.mockk.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class TVsRepositoryImplTest {

    // region Setup
    private lateinit var tvLocalDataSource: TVLocalDataSource
    private lateinit var tvRemoteDataSource: TVRemoteDataSource
    private lateinit var tvsRepository: TVsRepositoryImpl

    @Before
    fun setUp() {
        tvLocalDataSource = mockk(relaxed = true)
        tvRemoteDataSource = mockk(relaxed = true)
        tvsRepository = TVsRepositoryImpl(tvLocalDataSource = tvLocalDataSource, tvRemoteDataSource = tvRemoteDataSource)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN refreshPopularTVs is called AND remote data doesn't contain an error THEN returns a success response`() = runTest {
        coEvery { tvRemoteDataSource.getPopularTVs() } returns eitherSuccess(data = fakeTVsDto)

        val result = tvsRepository.refreshPopularTVs()

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { tvRemoteDataSource.getPopularTVs() }
        coVerify(exactly = 1) { tvLocalDataSource.insertAll(tvs = fakeTVsDto.asDatabaseModel()) }
        confirmVerified(tvRemoteDataSource, tvLocalDataSource)
    }

    @Test
    fun `WHEN refreshPopularTV is called AND remote data contains an error THEN returns a failure response`() = runTest {
        coEvery { tvRemoteDataSource.getPopularTVs() } returns fakeCustomError

        val result = tvsRepository.refreshPopularTVs()

        assertTrue(result.isFailure)
        coVerify(exactly = 1) { tvRemoteDataSource.getPopularTVs() }
        coVerify(exactly = 0) { tvLocalDataSource.insertAll(tvs = any()) }
        confirmVerified(tvRemoteDataSource, tvLocalDataSource)
    }

    @Test
    fun `WHEN getTVs is called THEN returns the TV list`() = runTest {
        every { tvLocalDataSource.getTVs() } returns flow { emit(value = fakeDatabaseTVs) }
        val expected = fakeDatabaseTVs.map { it.toTVModel() }

        tvsRepository.getTVs().test {
            val result: List<TVModel> = awaitItem()
            awaitComplete()

            assertTrue(result.isNotEmpty())
            assertEquals(expected, result)
            coVerify(exactly = 1) { tvLocalDataSource.getTVs() }
            confirmVerified(tvLocalDataSource)
        }
    }

    @Test
    fun `WHEN getFavoriteTVs is called THEN returns the favorite TV list`() = runTest {
        val favoriteDatabaseTVs = fakeDatabaseTVs.filter { it.isFavorite }
        every { tvLocalDataSource.getFavoriteTVs() } returns flow { emit(value = favoriteDatabaseTVs) }
        val expected = favoriteDatabaseTVs.map { it.toTVModel() }

        tvsRepository.getFavoriteTVs().test {
            val result: List<TVModel> = awaitItem()
            awaitComplete()

            assertTrue(result.isNotEmpty())
            assertEquals(expected, result)
            coVerify(exactly = 1) { tvLocalDataSource.getFavoriteTVs() }
            confirmVerified(tvLocalDataSource)
        }
    }
    // endregion
}