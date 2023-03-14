package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate.Default
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.MainCoroutinesRule
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class LoadDataUseCaseTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private val repository: MoviesRepository = mockk()
  private val globalErrorMapper: GlobalErrorMapper = mockk()
  private val globalErrorManager: GlobalErrorManager = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate = Default(globalErrorManager)
  private val useCaseToTest: LoadDataUseCase =
      LoadDataUseCase(repository, globalErrorMapper, coroutineTestRule.testDispatcherProvider)

  private val fakeDomainError = DomainError("fakeErrorCode", "fakeErrorMessage")

  @After
  fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }
  // endregion

  // region Testing
  @Test
  fun `with MOVIE search type as input, WHEN invoke the corresponding service successfully, THEN the use case returns a success response`() =
      runBlocking {
        // Given:
        every { repository.refreshMostPopularMovies() } returns eitherSuccess(Unit)

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(MOVIE).collect { assert(it.isSuccess) }
          }
        }

        // Then:
        verify(exactly = 1) { repository.refreshMostPopularMovies() }
        verify(exactly = 0) { repository.refreshMostPopularTVs() }
        confirmVerified(repository)
      }

  @Test
  fun `with MOVIE search type as input, WHEN invoke the corresponding service unsuccessfully, THEN the use case returns a failure response`() =
      runBlocking {
        // Given:
        every { repository.refreshMostPopularMovies() } returns eitherFailure(fakeDomainError)

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(MOVIE).collect { assert(it.isFailure) }
          }
        }

        // Then:
        verify(exactly = 1) { repository.refreshMostPopularMovies() }
        verify(exactly = 0) { repository.refreshMostPopularTVs() }
        confirmVerified(repository)
      }

  @Test
  fun `with SERIES search type as input, WHEN invoke the corresponding service successfully, THEN the use case returns a success response`() =
      runBlocking {
        // Given:
        every { repository.refreshMostPopularTVs() } returns eitherSuccess(Unit)

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(SERIES).collect { assert(it.isSuccess) }
          }
        }

        // Then:
        verify(exactly = 0) { repository.refreshMostPopularMovies() }
        verify(exactly = 1) { repository.refreshMostPopularTVs() }
        confirmVerified(repository)
      }

  @Test
  fun `with SERIES search type as input, WHEN invoke the corresponding service unsuccessfully, THEN the use case returns a failure response`() =
      runBlocking {
        // Given:
        every { repository.refreshMostPopularTVs() } returns eitherFailure(fakeDomainError)

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(SERIES).collect { assert(it.isFailure) }
          }
        }

        // Then:
        verify(exactly = 0) { repository.refreshMostPopularMovies() }
        verify(exactly = 1) { repository.refreshMostPopularTVs() }
        confirmVerified(repository)
      }
  // endregion
}
