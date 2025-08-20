package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.TVDetailRepository
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fakeDomainError
import com.manishsputnikcorporation.movies.utils.fakeMovieDetailModelId
import com.manishsputnikcorporation.movies.utils.fakeTVDetailModelId
import com.manishsputnikcorporation.movies.utils.movieDetailsArgs
import com.manishsputnikcorporation.movies.utils.tvDetailsArgs
import io.mockk.*
import junit.framework.TestCase.assertTrue
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class SetMovieFavoriteStatusUseCaseTest {

    // region Setup
    @get:Rule val coroutineTestRule = MainCoroutinesRule()

    private lateinit var movieDetailRepository: MovieDetailRepository
    private lateinit var tvDetailRepository: TVDetailRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: SetFavoriteStatusUseCase

    @Before
    fun setUp() {
        movieDetailRepository = mockk(relaxed = true)
        tvDetailRepository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            SetFavoriteStatusUseCase(
                movieDetailRepository = movieDetailRepository,
                tvDetailRepository = tvDetailRepository,
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
    fun `WHEN use case is called successfully with a MOVIE type THEN returns a success response with the favorite status`() = runTest {
        val expected = true
        coEvery { movieDetailRepository.updateMovie(fakeMovieDetailModelId) } returns eitherSuccess(data = expected)

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = movieDetailsArgs).collect { response ->
                assertTrue(response.isSuccess)
                response.mapSuccess { favoriteStatus -> assertEquals(expected, favoriteStatus) }
            }
        }

        coVerify(exactly = 1) { movieDetailRepository.updateMovie(fakeMovieDetailModelId) }
        coVerify(exactly = 0) { tvDetailRepository.updateTV(fakeMovieDetailModelId) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }

    @Test
    fun `WHEN use case is called unsuccessfully with a MOVIE type THEN returns a failure response`() = runTest {
        coEvery { movieDetailRepository.updateMovie(fakeMovieDetailModelId) } returns fakeDomainError

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = movieDetailsArgs).collect { assertTrue(it.isFailure) }
        }

        coVerify(exactly = 1) { movieDetailRepository.updateMovie(fakeMovieDetailModelId) }
        coVerify(exactly = 0) { tvDetailRepository.updateTV(fakeMovieDetailModelId) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }

    @Test
    fun `WHEN use case is called successfully with a TV type THEN returns a success response with the favorite status`() = runTest {
        val expected = true
        coEvery { tvDetailRepository.updateTV(fakeMovieDetailModelId) } returns eitherSuccess(data = expected)

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = tvDetailsArgs).collect { response ->
                assertTrue(response.isSuccess)
                response.mapSuccess { favoriteStatus -> assertEquals(expected, favoriteStatus) }
            }
        }

        coVerify(exactly = 0) { movieDetailRepository.updateMovie(id = any()) }
        coVerify(exactly = 1) { tvDetailRepository.updateTV(id = fakeTVDetailModelId) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }

    @Test
    fun `WHEN use case is called unsuccessfully with a TV type THEN returns a failure response`() = runTest {
        coEvery { tvDetailRepository.updateTV(fakeMovieDetailModelId) } returns fakeDomainError

        with(receiver = safeFlowUseCaseDelegate) { useCaseToTest.safePrepare(input = tvDetailsArgs).collect { assertTrue(it.isFailure) } }

        coVerify(exactly = 0) { movieDetailRepository.updateMovie(id = any()) }
        coVerify(exactly = 1) { tvDetailRepository.updateTV(id = fakeTVDetailModelId) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }
    // endregion
}
