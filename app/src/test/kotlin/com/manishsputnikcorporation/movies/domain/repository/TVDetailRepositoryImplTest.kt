package com.manishsputnikcorporation.movies.domain.repository

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSource
import com.manishsputnikcorporation.movies.data.repository.TVDetailRepositoryImpl
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.network.model.TVDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTV
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVModel
import com.manishsputnikcorporation.movies.utils.fakeCustomError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before

internal class TVDetailRepositoryImplTest {

    // region Setup
    private lateinit var tvLocalDataSource: TVLocalDataSource
    private lateinit var tvRemoteDataSource: TVRemoteDataSource
    private lateinit var tvDetailMapper: TVDetailDtoMapper
    private lateinit var tvDetailRepository: TVDetailRepositoryImpl

    private companion object {
        val fakeDatabaseTVId = fakeDatabaseTV.id
    }

    @Before
    fun setUp() {
        tvLocalDataSource = mockk(relaxed = true)
        tvRemoteDataSource = mockk(relaxed = true)
        tvDetailMapper = mockk(relaxed = true)
        tvDetailRepository =
            TVDetailRepositoryImpl(
                tvLocalDataSource = tvLocalDataSource,
                tvRemoteDataSource = tvRemoteDataSource,
                tvDetailMapper = tvDetailMapper,
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN getTVDetail is called AND remote data doesn't contain an error THEN returns a success response`() {
        every { tvRemoteDataSource.getTV(id = fakeDatabaseTVId) } returns eitherSuccess(data = fakeTVDetailDto)

        val result = tvDetailRepository.getTVDetail(id = fakeDatabaseTVId)

        assert(result.isSuccess)
        verify(exactly = 1) { tvRemoteDataSource.getTV(id = fakeDatabaseTVId) }
        verify(exactly = 1) { tvDetailMapper.mapToDomainModel(model = fakeTVDetailDto) }
        confirmVerified(tvRemoteDataSource, tvDetailMapper)
    }

    @Test
    fun `WHEN getTVDetail is called AND remote data contains an error THEN returns a failure response`() {
        every { tvRemoteDataSource.getTV(id = fakeDatabaseTVId) } returns fakeCustomError

        val result = tvDetailRepository.getTVDetail(id = fakeDatabaseTVId)

        assert(result.isFailure)
        verify(exactly = 1) { tvRemoteDataSource.getTV(id = fakeDatabaseTVId) }
        verify(exactly = 0) { tvDetailMapper.mapToDomainModel(model = any()) }
        confirmVerified(tvRemoteDataSource, tvDetailMapper)
    }

    @Test
    fun `WHEN isFavoriteTV is called AND the TV ID belongs to a favorite TV THEN returns true`() = runTest {
        every { tvLocalDataSource.isFavoriteTV(id = fakeDatabaseTVId) } returns flow { emit(value = true) }

        tvDetailRepository.isFavoriteTV(id = fakeDatabaseTVId).test {
            val result: Boolean = awaitItem()
            awaitComplete()

            assertTrue(result)
            coVerify(exactly = 1) { tvLocalDataSource.isFavoriteTV(id = fakeDatabaseTVId) }
            confirmVerified(tvLocalDataSource)
        }
    }

    @Test
    fun `WHEN isFavoriteTV is called AND the TV ID doesn't belong to a favorite TV THEN returns a false`() = runTest {
        every { tvLocalDataSource.isFavoriteTV(id = fakeDatabaseTVId) } returns flow { emit(value = false) }

        tvDetailRepository.isFavoriteTV(id = fakeDatabaseTVId).test {
            val result: Boolean = awaitItem()
            awaitComplete()

            assertFalse(result)
            coVerify(exactly = 1) { tvLocalDataSource.isFavoriteTV(id = fakeDatabaseTVId) }
            confirmVerified(tvLocalDataSource)
        }
    }

    @Test
    fun `WHEN getTV is called THEN returns the corresponding TV`() = runTest {
        every { tvLocalDataSource.getTV(id = fakeDatabaseTVId) } returns flow { emit(value = fakeDatabaseTV) }

        tvDetailRepository.getTV(id = fakeDatabaseTVId).test {
            val result: TVModel = awaitItem()
            awaitComplete()

            assertEquals(fakeTVModel, result)
            coVerify(exactly = 1) { tvLocalDataSource.getTV(id = fakeDatabaseTVId) }
            confirmVerified(tvLocalDataSource)
        }
    }

    @Test
    fun `WHEN updateTV is called AND the database update process is successful THEN returns a success response`() = runTest {
        val updatedTV = fakeDatabaseTV.copy(isFavorite = !fakeDatabaseTV.isFavorite)
        every { tvLocalDataSource.getTV(fakeDatabaseTVId) } returns flow { emit(value = fakeDatabaseTV) }
        coEvery { tvLocalDataSource.updateTV(tv = updatedTV) } returns Unit

        val result = tvDetailRepository.updateTV(fakeDatabaseTVId)

        assert(result.isSuccess)
        result.mapSuccess { updatedTVFavoriteStatus -> assertEquals(updatedTV.isFavorite, updatedTVFavoriteStatus) }
        verify(exactly = 1) { tvLocalDataSource.getTV(id = fakeDatabaseTVId) }
        coVerify(exactly = 1) { tvLocalDataSource.updateTV(tv = updatedTV) }
        confirmVerified(tvLocalDataSource)
    }

    @Test
    fun `WHEN updateTV is called AND the database update process is unsuccessful THEN returns a failure response`() = runTest {
        val updatedTV = fakeDatabaseTV.copy(isFavorite = !fakeDatabaseTV.isFavorite)
        every { tvLocalDataSource.getTV(fakeDatabaseTVId) } returns flow { emit(value = fakeDatabaseTV) }
        coEvery { tvLocalDataSource.updateTV(tv = updatedTV) } throws Exception()

        val result = tvDetailRepository.updateTV(fakeDatabaseTVId)

        assert(result.isFailure)
        verify(exactly = 1) { tvLocalDataSource.getTV(fakeDatabaseTVId) }
        coVerify(exactly = 1) { tvLocalDataSource.updateTV(tv = updatedTV) }
        confirmVerified(tvLocalDataSource)
    }
    // endregion
}
