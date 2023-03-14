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
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class SetMovieFavoriteStatusUseCaseTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private val repository: MovieDetailRepository = mockk()
  private val globalErrorMapper: GlobalErrorMapper = mockk()
  private val globalErrorManager: GlobalErrorManager = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate = Default(globalErrorManager)
  private val useCaseToTest: SetMovieFavoriteStatusUseCase =
      SetMovieFavoriteStatusUseCase(
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
  fun `with ID as input, WHEN invoke the update service successfully, THEN the use case returns a success response`() =
      runBlocking {
        // Given:
        coEvery { repository.updateMovie(fakeId) } returns eitherSuccess(true)

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(fakeId).collect { assert(it.isSuccess) }
          }
        }

        // Then:
        coVerify(exactly = 1) { repository.updateMovie(fakeId) }
        confirmVerified(repository)
      }

  @Test
  fun `with ID as input, WHEN invoke the update service unsuccessfully, THEN the use case returns a failure response`() =
      runBlocking {
        // Given:
        coEvery { repository.updateMovie(fakeId) } returns
            eitherFailure(DomainError("fakeErrorCode", "fakeErrorMessage"))

        // When:
        coroutineTestRule.runTest {
          with(safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(fakeId).collect { assert(it.isFailure) }
          }
        }

        // Then:
        coVerify(exactly = 1) { repository.updateMovie(fakeId) }
        confirmVerified(repository)
      }
  // endregion
}
