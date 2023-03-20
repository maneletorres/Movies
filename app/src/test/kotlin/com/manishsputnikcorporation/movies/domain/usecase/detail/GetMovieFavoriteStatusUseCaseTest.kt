package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate.Default
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.MainCoroutinesRule
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetMovieFavoriteStatusUseCaseTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private val repository: MovieDetailRepository = mockk()
  private val globalErrorMapper: GlobalErrorMapper = mockk()
  private val globalErrorManager: GlobalErrorManager = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate = Default(globalErrorManager)
  private val useCaseToTest: GetMovieFavoriteStatusUseCase =
      GetMovieFavoriteStatusUseCase(
          repository, globalErrorMapper, coroutineTestRule.testDispatcherProvider)

  private val fakeId = fakeMovieDetailModel.id

  @After
  fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }
  // endregion

  // region Testing
  @Test
  fun `with ID as input, WHEN the movie is favorite, THEN the use case returns a true`() = runTest {
    // Given:
    every { repository.isFavoriteMovie(fakeId) } returns flow { emit(true) }

    // When:
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        useCaseToTest.safePrepare(fakeId).collect { isFavorite -> assertTrue(isFavorite) }
      }
    }

    // Then:
    verify(exactly = 1) { repository.isFavoriteMovie(fakeId) }
    confirmVerified(repository)
  }

  @Test
  fun `with ID as input, WHEN the movie isn't favorite, THEN the use case returns a false`() =
      runTest {
        // Given:
        every { repository.isFavoriteMovie(fakeId) } returns flow { emit(false) }

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(fakeId).collect { isFavorite -> assertFalse(isFavorite) }
          }
        }

        // Then:
        verify(exactly = 1) { repository.isFavoriteMovie(fakeId) }
        confirmVerified(repository)
      }
  // endregion
}
