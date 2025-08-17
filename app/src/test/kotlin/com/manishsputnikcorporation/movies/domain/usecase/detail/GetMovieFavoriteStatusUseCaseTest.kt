package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
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

    private lateinit var repository: MovieDetailRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: GetMovieFavoriteStatusUseCase

    private val fakeMovieDetailModelId = fakeMovieDetailModel.id

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            GetMovieFavoriteStatusUseCase(
                movieDetailRepository = repository,
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
    fun `WHEN use case is called successfully AND the movie is favorite THEN returns true`() = runTest {
        every { repository.isFavoriteMovie(fakeMovieDetailModelId) } returns flow { emit(value = true) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = fakeMovieDetailModelId).collect { isFavorite -> assertTrue(isFavorite) }
        }

        coVerify(exactly = 1) { repository.isFavoriteMovie(fakeMovieDetailModelId) }
        confirmVerified(repository)
    }

    @Test
    fun `WHEN use case is called successfully AND the movie is not favorite THEN returns false`() = runTest {
        every { repository.isFavoriteMovie(fakeMovieDetailModelId) } returns flow { emit(value = false) }

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = fakeMovieDetailModelId).collect { isFavorite -> assertFalse(isFavorite) }
        }

        coVerify(exactly = 1) { repository.isFavoriteMovie(fakeMovieDetailModelId) }
        confirmVerified(repository)
    }
    // endregion
}
