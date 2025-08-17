package com.manishsputnikcorporation.movies.ui.screens.detail

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import com.manishsputnikcorporation.movies.utils.fakeFlowDomainError
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class MovieDetailViewModelTest {

    // region Setup
    private lateinit var viewModel: MovieDetailViewModel
    private val analyticsManager: AnalyticsManager = mockk()
    private val getMovieDetailUseCase: GetMovieDetailUseCase = mockk()
    private val getMovieFavoriteStatusUseCase: GetMovieFavoriteStatusUseCase = mockk()
    private val setMovieFavoriteStatusUseCase: SetMovieFavoriteStatusUseCase = mockk()
    private val safeFlowUseCaseDelegate: FlowUseCaseDelegate = mockk()

    private val fakeMovieDetailModelId = fakeMovieDetailModel.id

    @Before
    fun setUp() {
        viewModel =
            MovieDetailViewModel(
                analyticsManager = analyticsManager,
                getMovieDetailUseCase = getMovieDetailUseCase,
                getMovieFavoriteStatusUseCase = getMovieFavoriteStatusUseCase,
                setMovieFavoriteStatusUseCase = setMovieFavoriteStatusUseCase,
                safeFlowUseCase = safeFlowUseCaseDelegate,
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN getMovieDetail is called successfully THEN updates the state with the movie detail data`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getMovieDetailUseCase.safePrepare(input = any()) } returns
                flow { emit(value = eitherSuccess(data = fakeMovieDetailModel)) }

            viewModel.movieDetailUiState.test {
                viewModel.getMovieDetail(id = fakeMovieDetailModelId)

                awaitItem().assertInitialState()
                with(receiver = awaitItem()) { assertEquals(false to true, isIdle to isLoading) }
                with(receiver = awaitItem()) { assertEquals(false to fakeMovieDetailModel, isLoading to movieDetailData.movieDetailModel) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getMovieDetail is called unsuccessfully THEN updates the state with isFailed true`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getMovieDetailUseCase.safePrepare(input = any()) } returns fakeFlowDomainError

            viewModel.movieDetailUiState.test {
                viewModel.getMovieDetail(id = fakeMovieDetailModelId)

                awaitItem().assertInitialState()
                with(receiver = awaitItem()) { assertEquals(false to true, isIdle to isLoading) }
                with(receiver = awaitItem()) { assertEquals(false to true, isLoading to isFailed) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getMovieFavoriteStatus is called THEN updates the state with the corresponding favorite status`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getMovieFavoriteStatusUseCase.safePrepare(input = any()) } returns flow { emit(value = true) }

            viewModel.movieDetailUiState.test {
                viewModel.getMovieFavoriteStatus(id = fakeMovieDetailModelId)

                awaitItem().assertInitialState()
                assertEquals(true, awaitItem().movieDetailData.isFavorite)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN setMovieFavoriteStatus is called successfully THEN updates the state with the corresponding favorite status`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { setMovieFavoriteStatusUseCase.safePrepare(input = any()) } returns flow { emit(value = eitherSuccess(true)) }

            viewModel.movieDetailUiState.test {
                viewModel.setMovieFavoriteStatus(fakeMovieDetailModelId)

                awaitItem().assertInitialState()
                assertEquals(true, awaitItem().movieDetailData.isFavorite)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN setMovieFavoriteStatus is called unsuccessfully THEN updates the state with the corresponding favorite status`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { setMovieFavoriteStatusUseCase.safePrepare(input = any()) } returns fakeFlowDomainError

            viewModel.movieDetailUiState.test {
                viewModel.setMovieFavoriteStatus(fakeMovieDetailModelId)

                awaitItem().assertInitialState()
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    // endregion

    // region Helper methods
    private fun MovieDetailUiState.assertInitialState() {
        assertEquals(true, isIdle)
        assertEquals(false, isLoading)
        assertEquals(null, movieDetailData.movieDetailModel)
        assertEquals(false, movieDetailData.isFavorite)
        assertEquals(false, isFailed)
    }
    // endregion
}
