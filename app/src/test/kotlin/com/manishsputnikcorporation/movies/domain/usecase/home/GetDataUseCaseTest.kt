package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.repository.TVsRepository
import com.manishsputnikcorporation.movies.utils.enums.Type.*
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
internal class GetDataUseCaseTest {

    // region Setup
    @get:Rule val coroutineTestRule = MainCoroutinesRule()

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var tvsRepository: TVsRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: GetDataUseCase

    @Before
    fun setUp() {
        moviesRepository = mockk(relaxed = true)
        tvsRepository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            GetDataUseCase(
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
    fun `WHEN use case is called with a MOVIE type as input THEN returns a movies`() = runTest {
        every { moviesRepository.getMovies() } returns flow { emit(value = fakeMovieModels) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = MOVIE).collect { movies ->
                assertTrue(movies.isNotEmpty())
                assertEquals(fakeMovieModels, movies)
            }
        }

        coVerify(exactly = 1) { moviesRepository.getMovies() }
        coVerify(exactly = 0) { tvsRepository.getTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }

    @Test
    fun `WHEN use case is called with a TV type as input THEN returns a TVs`() = runTest {
        every { tvsRepository.getTVs() } returns flow { emit(value = fakeTVModels) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = TV).collect { tvs ->
                assertTrue(tvs.isNotEmpty())
                assertEquals(fakeTVModels, tvs)
            }
        }

        coVerify(exactly = 0) { moviesRepository.getMovies() }
        coVerify(exactly = 1) { tvsRepository.getTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }
    // endregion
}
