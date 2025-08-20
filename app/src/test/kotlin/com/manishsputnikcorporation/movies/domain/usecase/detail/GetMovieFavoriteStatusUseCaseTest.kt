package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.TVDetailRepository
import com.manishsputnikcorporation.movies.utils.fakeMovieDetailModelId
import com.manishsputnikcorporation.movies.utils.fakeTVDetailModelId
import com.manishsputnikcorporation.movies.utils.movieDetailsArgs
import com.manishsputnikcorporation.movies.utils.tvDetailsArgs
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetMovieFavoriteStatusUseCaseTest {

    // region Setup
    @get:Rule val coroutineTestRule = MainCoroutinesRule()

    private lateinit var movieDetailRepository: MovieDetailRepository
    private lateinit var tvDetailRepository: TVDetailRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: GetFavoriteStatusUseCase

    @Before
    fun setUp() {
        movieDetailRepository = mockk(relaxed = true)
        tvDetailRepository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            GetFavoriteStatusUseCase(
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
    fun `WHEN use case is called successfully with a MOVIE type AND the movie is favorite THEN returns true`() = runTest {
        every { movieDetailRepository.isFavoriteMovie(fakeMovieDetailModelId) } returns flow { emit(value = true) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = movieDetailsArgs).collect { isFavorite -> assertTrue(isFavorite) }
        }

        coVerify(exactly = 1) { movieDetailRepository.isFavoriteMovie(fakeMovieDetailModelId) }
        coVerify(exactly = 0) { tvDetailRepository.isFavoriteTV(any()) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }

    @Test
    fun `WHEN use case is called successfully with a MOVIE type AND the movie is not favorite THEN returns false`() = runTest {
        every { movieDetailRepository.isFavoriteMovie(fakeMovieDetailModelId) } returns flow { emit(value = false) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = movieDetailsArgs).collect { isFavorite -> assertFalse(isFavorite) }
        }

        coVerify(exactly = 1) { movieDetailRepository.isFavoriteMovie(fakeMovieDetailModelId) }
        coVerify(exactly = 0) { tvDetailRepository.isFavoriteTV(any()) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }

    @Test
    fun `WHEN use case is called successfully with a TV type AND the movie is favorite THEN returns true`() = runTest {
        every { tvDetailRepository.isFavoriteTV(fakeTVDetailModelId) } returns flow { emit(value = true) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = tvDetailsArgs).collect { isFavorite -> assertTrue(isFavorite) }
        }

        coVerify(exactly = 0) { movieDetailRepository.isFavoriteMovie(id = any()) }
        coVerify(exactly = 1) { tvDetailRepository.isFavoriteTV(id = fakeTVDetailModelId) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }

    @Test
    fun `WHEN use case is called successfully with a TV type AND the movie is not favorite THEN returns false`() = runTest {
        every { tvDetailRepository.isFavoriteTV(fakeTVDetailModelId) } returns flow { emit(value = false) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = tvDetailsArgs).collect { isFavorite -> assertFalse(isFavorite) }
        }

        coVerify(exactly = 0) { movieDetailRepository.isFavoriteMovie(id = any()) }
        coVerify(exactly = 1) { tvDetailRepository.isFavoriteTV(id = fakeTVDetailModelId) }
        confirmVerified(movieDetailRepository, tvDetailRepository)
    }
    // endregion
}
