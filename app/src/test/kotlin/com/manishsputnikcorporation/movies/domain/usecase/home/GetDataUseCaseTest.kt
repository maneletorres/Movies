package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.getDomainMovieList
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitParamsRunner::class)
internal class GetDataUseCaseTest {

  // region Setup
  @get:Rule val coroutineTestRule = MainCoroutinesRule()

  private val repository: MoviesRepository = mockk()
  private val globalErrorMapper: GlobalErrorMapper = mockk()
  private val globalErrorManager: GlobalErrorManager = mockk()
  private val safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate =
      SafeFlowUseCaseDelegate.Default(globalErrorManager)
  private val useCaseToTest: GetDataUseCase =
      GetDataUseCase(repository, globalErrorMapper, coroutineTestRule.testDispatcherProvider)

  @After
  fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }
  // endregion

  // region Testing
  @Test
  @Parameters(method = "searchTypeScenario")
  fun `with search type as input, WHEN invoke the service, THEN the use case returns the corresponding list`(
      searchType: Type
  ) = runTest {
    // Given:
    every { repository.getData(searchType) } returns getDomainMovieList(searchType)

    // When:
    coroutineTestRule.runTest {
      with(safeFlowUseCaseDelegate) {
        useCaseToTest.safePrepare(searchType).collect {
          assert(it.isNotEmpty())
          assert(it.all { movieModel -> movieModel.type == searchType })
        }
      }
    }

    // Then:
    verify(exactly = 1) { repository.getData(searchType) }
    confirmVerified(repository)
  }
  // endregion

  // region Helper methods
  @SuppressWarnings("unused") private fun searchTypeScenario() = listOf(MOVIE, SERIES)
  // endregion
}
