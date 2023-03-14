package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate.Default
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.usecase.MainCoroutinesRule
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetMovieDetailUseCaseTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private val repository: MovieDetailRepository = mockk()
  private val globalErrorMapper: GlobalErrorMapper = mockk()
  private val globalErrorManager: GlobalErrorManager = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate = Default(globalErrorManager)
  private val useCaseToTest: GetMovieDetailUseCase =
      GetMovieDetailUseCase(repository, globalErrorMapper, coroutineTestRule.testDispatcherProvider)

  private val fakeId = fakeMovieDetailModel.id

  @After
  fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }
  // endregion

  // region Testing
  @Test
  fun `with ID as input, WHEN invoke the corresponding service successfully, THEN the use case returns a success response`() =
      runBlocking {
        // Given:
        every { repository.getMovieDetail(fakeId) } returns eitherSuccess(fakeMovieDetailModel)

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(fakeId).collect { assert(it.isSuccess) }
          }
        }

        // Then:
        verify(exactly = 1) { repository.getMovieDetail(fakeId) }
        confirmVerified(repository)
      }

  @Test
  fun `with ID as input, WHEN invoke the corresponding service unsuccessfully, THEN the use case returns a failure response`() =
      runBlocking {
        // Given:
        every { repository.getMovieDetail(fakeId) } returns
            eitherFailure(DomainError("fakeErrorCode", "fakeErrorMessage"))

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(fakeId).collect { assert(it.isFailure) }
          }
        }

        // Then:
        verify(exactly = 1) { repository.getMovieDetail(fakeId) }
        confirmVerified(repository)
      }
  // endregion
}
