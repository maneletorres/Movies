package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.utils.getDomainMovieList
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetFavoriteDataUseCaseTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private val repository: MoviesRepository = mockk()
  private val globalErrorMapper: GlobalErrorMapper = mockk()
  private val globalErrorManager: GlobalErrorManager = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate =
      SafeFlowUseCaseDelegate.Default(globalErrorManager)
  private val useCaseToTest: GetFavoriteDataUseCase =
      GetFavoriteDataUseCase(
          repository, globalErrorMapper, coroutineTestRule.testDispatcherProvider)

  @After
  fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }
  // endregion

  // region Testing
  @Test
  fun `WHEN invoke the service, THEN the use case returns the list of favorites`() = runTest {
    // Given:
    every { repository.getFavorites() } returns getDomainMovieList()

    // When:
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        useCaseToTest.safePrepare(Unit).collect {
          assert(it.isNotEmpty())
          assert(it.all { movieModel -> movieModel.isFavorite })
        }
      }
    }

    // Then:
    verify(exactly = 1) { repository.getFavorites() }
    confirmVerified(repository)
  }
  // endregion
}
