package com.manishsputnikcorporation.movies.ui.screens.home

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.base.Model
import com.manishsputnikcorporation.movies.domain.usecase.home.GetDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.GetFavoriteDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.LoadDataUseCase
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.MoviesHomeUiState.*
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModels
import com.manishsputnikcorporation.movies.utils.fake.fakeTVModels
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import io.mockk.*
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(Parameterized::class)
internal class HomeViewModelTest(private val searchType: Type) {

    // region Setup
    companion object {
        @JvmStatic @Parameterized.Parameters fun data() = listOf(MOVIE, TV)
    }

    private lateinit var analyticsManager: AnalyticsManager
    private lateinit var authManager: AuthManager
    private lateinit var loadDataUseCase: LoadDataUseCase
    private lateinit var getDataUseCase: GetDataUseCase
    private lateinit var getFavoriteDataUseCase: GetFavoriteDataUseCase
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        analyticsManager = mockk(relaxed = true)
        authManager = mockk(relaxed = true)
        loadDataUseCase = mockk(relaxed = true)
        getDataUseCase = mockk(relaxed = true)
        getFavoriteDataUseCase = mockk(relaxed = true)
        safeFlowUseCaseDelegate = mockk(relaxed = true)
        viewModel =
            HomeViewModel(
                analyticsManager = analyticsManager,
                authManager = authManager,
                loadDataUseCase = loadDataUseCase,
                getDataUseCase = getDataUseCase,
                getFavoriteDataUseCase = getFavoriteDataUseCase,
                safeFlowUseCase = safeFlowUseCaseDelegate,
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
    fun `WHEN loadData is called successfully THEN getData is called AND returns a Data status with the media model list`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            val expected = getExpectedFakeData()
            coEvery { loadDataUseCase.safePrepare(input = searchType) } returns flow { emit(value = eitherSuccess(data = Unit)) }
            coEvery { getDataUseCase.safePrepare(input = searchType) } returns flow { emit(value = expected) }

            viewModel.moviesHomeUiState.test {
                viewModel.loadData(type = searchType)

                assertTrue(awaitItem() is Idle)
                assertTrue(awaitItem() is Loading)
                with(receiver = awaitItem()) {
                    assertTrue(this is Data)
                    assertEquals(expected, mediaModelList)
                }
                coVerify(exactly = 1) { authManager.getCurrentUser() }
                confirmVerified(authManager)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN loadData is called unsuccessfully THEN an error event is sent`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            coEvery { loadDataUseCase.safePrepare(input = searchType) } returns flow { throw Exception() }

            viewModel.moviesHomeUiState.test {
                viewModel.loadData(type = searchType)

                assertTrue(awaitItem() is Idle)
                assertTrue(awaitItem() is Loading)

                viewModel.event.test { assertTrue(awaitItem() is HomeViewModel.Event.Error) }

                coVerify(exactly = 0) { authManager.getCurrentUser() }
                confirmVerified(authManager)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getData is called successfully THEN returns a Data status with the media model list`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            val expected = getExpectedFakeData()
            coEvery { getDataUseCase.safePrepare(input = searchType) } returns flow { emit(value = expected) }

            viewModel.moviesHomeUiState.test {
                viewModel.getData(type = searchType)

                assertTrue(awaitItem() is Idle)
                with(receiver = awaitItem()) {
                    assertTrue(this is Data)
                    assertEquals(expected, mediaModelList)
                }
                coVerify(exactly = 1) { authManager.getCurrentUser() }
                confirmVerified(authManager)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getData is called unsuccessfully THEN returns an error event is sent`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            coEvery { getDataUseCase.safePrepare(input = searchType) } returns flow { throw Exception() }

            viewModel.moviesHomeUiState.test {
                viewModel.getData(type = searchType)

                assertTrue(awaitItem() is Idle)

                viewModel.event.test { assertTrue(awaitItem() is HomeViewModel.Event.Error) }

                coVerify(exactly = 0) { authManager.getCurrentUser() }
                confirmVerified(authManager)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getFavorites is called successfully THEN returns a Data status with the favorite media model list`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            val expected = getExpectedFakeData()
            coEvery { getFavoriteDataUseCase.safePrepare(input = searchType) } returns flow { emit(value = expected) }

            viewModel.moviesHomeUiState.test {
                viewModel.getFavorites(type = searchType)

                assertTrue(awaitItem() is Idle)
                with(receiver = awaitItem()) {
                    assertTrue(this is Data)
                    assertEquals(expected, mediaModelList)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getFavorites is called unsuccessfully THEN returns an error event is sent`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            coEvery { getFavoriteDataUseCase.safePrepare(input = searchType) } returns flow { throw Exception() }

            viewModel.moviesHomeUiState.test {
                viewModel.getFavorites(type = searchType)

                assertTrue(awaitItem() is Idle)

                viewModel.event.test { assertTrue(awaitItem() is HomeViewModel.Event.Error) }

                cancelAndConsumeRemainingEvents()
            }
        }
    }

    // endregion

    // region Helper methods
    private fun getExpectedFakeData(): List<Model> =
        when (searchType) {
            MOVIE -> fakeMovieModels
            TV -> fakeTVModels
        }
    // endregion
}
