package com.manishsputnikcorporation.movies.ui.screens.home

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.usecase.home.GetDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.GetFavoriteDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.LoadDataUseCase
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.Event.*
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.*
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.fakeFlowDomainError
import com.manishsputnikcorporation.movies.utils.getDomainMovieList
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitParamsRunner::class)
internal class MoviesHomeViewModelTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private lateinit var viewModel: MoviesHomeViewModel
  private val loadDataUseCase: LoadDataUseCase = mockk()
  private val getDataUseCase: GetDataUseCase = mockk()
  private val getFavoriteDataUseCase: GetFavoriteDataUseCase = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate = mockk()

  @Before
  fun setUp() {
    viewModel =
        MoviesHomeViewModel(
            loadDataUseCase, getDataUseCase, getFavoriteDataUseCase, safeFlowUseCaseDelegate)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }
  // endregion

  // region Testing
  @Test
  fun `on certain search type as input WHEN load data ends successfully THEN returns a success response`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { loadDataUseCase.safePrepare(any()) } returns flow { emit(eitherSuccess(Unit)) }
        every { getDataUseCase.safePrepare(any()) } returns flow { emit(emptyList()) }

        viewModel.moviesHomeUiState.test {
          // When:
          viewModel.loadData()

          // Then:
          assert(awaitItem() is Idle)
          assert(awaitItem() is Loading)
          assert(awaitItem() is Data)
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) {
          loadDataUseCase.safePrepare(any())
          getDataUseCase.safePrepare(any())
        }
        confirmVerified(
            getDataUseCase) // TODO: doesn't work. It needs confirmVerified loadDataUseCase too!
      }
    }
  }

  @Test
  fun `on certain search type as input WHEN load data ends unsuccessfully THEN returns an Error event`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        every { loadDataUseCase.safePrepare(any()) } returns fakeFlowDomainError
        every { getDataUseCase.safePrepare(any()) } returns flow { emit(emptyList()) }

        viewModel.event.test {
          viewModel.moviesHomeUiState.test {
            // When:
            viewModel.loadData()

            // Then:
            assert(awaitItem() is Idle)
            assert(awaitItem() is Loading)
            cancelAndConsumeRemainingEvents()
          }
          assert(awaitItem() is Error)
        }

        verify(exactly = 1) {
          loadDataUseCase.safePrepare(any())
          getDataUseCase.safePrepare(any())
        }
        confirmVerified(
            getDataUseCase) // TODO: doesn't work. It needs confirmVerified loadDataUseCase too!
      }
    }
  }

  @Test
  @Parameters(method = "searchTypeScenario")
  fun `on certain search type as input WHEN get data ends THEN returns the corresponding list`(
      searchType: Type
  ) {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        val fakeList = getDomainMovieList(searchType)
        every { getDataUseCase.safePrepare(searchType) } returns fakeList

        viewModel.moviesHomeUiState.test {
          // When:
          viewModel.getData(searchType)

          // Then:
          assert(awaitItem() is Idle)
          awaitItem().apply {
            assert(this is Data)
            with((this as Data).movieList) {
              assertEquals(fakeList.first(), this)
              assert(all { it.type == searchType })
            }
          }
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { getDataUseCase.safePrepare(searchType) }
        confirmVerified(getDataUseCase)
      }
    }
  }

  @Test
  fun `WHEN get favorites ends THEN returns the favorite list`() {
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        // Given:
        val fakeList = getDomainMovieList()
        every { getFavoriteDataUseCase.safePrepare<Unit, List<MovieModel>>() } returns fakeList

        viewModel.moviesHomeUiState.test {
          // When:
          viewModel.getFavorites()

          // Then:
          assert(awaitItem() is Idle)
          awaitItem().apply {
            assert(this is Data)
            with((this as Data).movieList) {
              assertEquals(fakeList.first(), this)
              assert(all { it.isFavorite })
            }
          }
          cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) { getFavoriteDataUseCase.safePrepare<Unit, List<MovieModel>>() }
        confirmVerified(getFavoriteDataUseCase)
      }
    }
  }
  // endregion

  // region Helper methods
  @SuppressWarnings("unused") private fun searchTypeScenario() = listOf(MOVIE, SERIES)
  // endregion
}
