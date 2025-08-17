package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.repository.TVsRepository
import com.manishsputnikcorporation.movies.utils.enums.Type.MOVIE
import com.manishsputnikcorporation.movies.utils.enums.Type.TV
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModels
import com.manishsputnikcorporation.movies.utils.fake.fakeTVModels
import io.mockk.*
import junit.framework.TestCase.assertTrue
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetFavoriteDataUseCaseTest {

    // region Setup
    @get:Rule val coroutineTestRule = MainCoroutinesRule()

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var tvsRepository: TVsRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: GetFavoriteDataUseCase

    @Before
    fun setUp() {
        moviesRepository = mockk(relaxed = true)
        tvsRepository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            GetFavoriteDataUseCase(
                moviesRepository = moviesRepository,
                tvsRepository = tvsRepository,
                globalErrorMapper = globalErrorMapper,
                dispatcherProvider = coroutineTestRule.testDispatcherProvider,
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN use case is called with a MOVIE type as input THEN returns the favorite movies`() = runTest {
        val expected = fakeMovieModels.filter { it.isFavorite }
        every { moviesRepository.getFavoriteMovies() } returns flow { emit(value = expected) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = MOVIE).collect { favoriteMovies ->
                assertTrue(favoriteMovies.isNotEmpty())
                assertEquals(expected, favoriteMovies)
            }
        }

        coVerify(exactly = 1) { moviesRepository.getFavoriteMovies() }
        coVerify(exactly = 0) { tvsRepository.getFavoriteTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }

    @Test
    fun `WHEN use case is called with a TV type as input THEN returns the favorite TVs`() = runTest {
        val expected = fakeTVModels.filter { it.isFavorite }
        every { tvsRepository.getFavoriteTVs() } returns flow { emit(value = expected) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = TV).collect { favoriteTVs ->
                assertTrue(favoriteTVs.isNotEmpty())
                assertEquals(expected, favoriteTVs)
            }
        }

        coVerify(exactly = 0) { moviesRepository.getFavoriteMovies() }
        coVerify(exactly = 1) { tvsRepository.getFavoriteTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }
    // endregion
}
