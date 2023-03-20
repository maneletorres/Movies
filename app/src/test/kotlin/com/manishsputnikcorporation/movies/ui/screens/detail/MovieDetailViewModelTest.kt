package com.manishsputnikcorporation.movies.ui.screens.detail

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import com.manishsputnikcorporation.movies.utils.fakeFlowDomainError
import io.mockk.*
import junitparams.JUnitParamsRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitParamsRunner::class)
internal class MovieDetailViewModelTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private lateinit var viewModel: MovieDetailViewModel
  private val getMovieDetailUseCase: GetMovieDetailUseCase = mockk()
  private val getMovieFavoriteStatusUseCase: GetMovieFavoriteStatusUseCase = mockk()
  private val setMovieFavoriteStatusUseCase: SetMovieFavoriteStatusUseCase = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate = mockk()

  @Before
  fun setUp() {
    viewModel =
        MovieDetailViewModel(
            getMovieDetailUseCase,
            getMovieFavoriteStatusUseCase,
            setMovieFavoriteStatusUseCase,
            safeFlowUseCaseDelegate)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }
  // endregion

  // region Testing
  @Test
  fun loadData() {
    // TODO:
  }

  @Test
  fun `on certain ID as input WHEN get movie detail ends successfully THEN returns a MovieDetailData as state in a success response`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { getMovieDetailUseCase.safePrepare(any()) } returns
            flow { emit(eitherSuccess(fakeMovieDetailModel)) }

        viewModel.movieDetailUiState.test {
          // When:
          viewModel.getMovieDetail(fakeMovieDetailModel.id)

          // Then:
          // Step 1 - Initial value:
          awaitItem().assertInitialState()

          // Step 2 - onStart:
          awaitItem().apply {
            assertEquals(false, isIdle)
            assertEquals(true, isLoading)
          }

          // Step 3 - onEach/onSuccess:
          assertEquals(fakeMovieDetailModel, awaitItem().movieDetailData.movieDetailModel)
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { getMovieDetailUseCase.safePrepare(any()) }
        confirmVerified(getMovieDetailUseCase)
      }
    }
  }

  @Test
  fun `on certain ID as input WHEN get movie detail ends unsuccessfully THEN returns true as state in a failure response`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { getMovieDetailUseCase.safePrepare(any()) } returns fakeFlowDomainError

        viewModel.movieDetailUiState.test {
          // When:
          viewModel.getMovieDetail(fakeMovieDetailModel.id)

          // Then:
          // Step 1 - Initial value:
          awaitItem().assertInitialState()

          // Step 2 - onStart:
          awaitItem().apply {
            assertEquals(false, isIdle)
            assertEquals(true, isLoading)
          }

          // Step 3 - onEach/onFailure:
          awaitItem().apply {
            assertEquals(false, isLoading)
            assertEquals(true, isFailed)
          }
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { getMovieDetailUseCase.safePrepare(any()) }
        confirmVerified(getMovieDetailUseCase)
      }
    }
  }

  @Test
  fun `on certain ID as input WHEN get movie favorite status THEN returns the corresponding favorite status`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { getMovieFavoriteStatusUseCase.safePrepare(any()) } returns flow { emit(true) }

        viewModel.movieDetailUiState.test {
          // When:
          viewModel.getMovieFavoriteStatus(fakeMovieDetailModel.id)

          // Then:
          // Step 1 - Initial value:
          awaitItem().assertInitialState()

          // Step 2 - onEach:
          assertEquals(true, awaitItem().movieDetailData.isFavorite)
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { getMovieFavoriteStatusUseCase.safePrepare(any()) }
        confirmVerified(getMovieFavoriteStatusUseCase)
      }
    }
  }

  @Test
  fun `on certain ID as input WHEN set movie favorite status ends successfully THEN returns the corresponding favorite status in a success response`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { setMovieFavoriteStatusUseCase.safePrepare(any()) } returns
            flow { emit(eitherSuccess(true)) }

        viewModel.movieDetailUiState.test {
          // When:
          viewModel.setMovieFavoriteStatus(fakeMovieDetailModel.id)

          // Then:
          // Step 1 - Initial value:
          awaitItem().assertInitialState()

          // Step 2 - onEach:
          assertEquals(true, awaitItem().movieDetailData.isFavorite)
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { setMovieFavoriteStatusUseCase.safePrepare(any()) }
        confirmVerified(setMovieFavoriteStatusUseCase)
      }
    }
  }

  @Test
  fun `on certain ID as input WHEN set movie favorite status ends unsuccessfully THEN returns nothing`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { setMovieFavoriteStatusUseCase.safePrepare(any()) } returns fakeFlowDomainError

        viewModel.movieDetailUiState.test {
          // When:
          viewModel.setMovieFavoriteStatus(fakeMovieDetailModel.id)

          // Then:
          // Step 1 - Initial value:
          awaitItem().assertInitialState()
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { setMovieFavoriteStatusUseCase.safePrepare(any()) }
        confirmVerified(setMovieFavoriteStatusUseCase)
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
