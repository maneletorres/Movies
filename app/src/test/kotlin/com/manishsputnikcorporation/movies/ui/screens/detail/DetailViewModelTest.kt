package com.manishsputnikcorporation.movies.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.DetailModel
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState.Data
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState.Empty
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState.Idle
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState.Loading
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.MOVIE
import com.manishsputnikcorporation.movies.utils.enums.Type.TV
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDetailModel
import com.manishsputnikcorporation.movies.utils.fakeFlowDomainError
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.movieDetailsArgs
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
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
internal class DetailViewModelTest(private val searchType: Type) {

    // region Setup
    companion object {
        @JvmStatic @Parameterized.Parameters fun data() = listOf(MOVIE, TV)
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var analyticsManager: AnalyticsManager
    private lateinit var getDetailUseCase: GetDetailUseCase
    private lateinit var getFavoriteStatusUseCase: GetFavoriteStatusUseCase
    private lateinit var setFavoriteStatusUseCase: SetFavoriteStatusUseCase
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var savedState: SavedStateHandle

    @Before
    fun setUp() {
        analyticsManager = mockk(relaxed = true)
        getDetailUseCase = mockk(relaxed = true)
        getFavoriteStatusUseCase = mockk(relaxed = true)
        setFavoriteStatusUseCase = mockk(relaxed = true)
        safeFlowUseCaseDelegate = mockk(relaxed = true)
        savedState = SavedStateHandle()
        viewModel =
            DetailViewModel(
                getDetailUseCase = getDetailUseCase,
                getFavoriteStatusUseCase = getFavoriteStatusUseCase,
                setFavoriteStatusUseCase = setFavoriteStatusUseCase,
                safeFlowUseCase = safeFlowUseCaseDelegate,
                savedStateHandle = savedState,
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN getDetail is called successfully THEN returns a Data state with the detail model`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            val expected = getExpectedFakeData()
            coEvery { getDetailUseCase.safePrepare(input = movieDetailsArgs) } returns flow { emit(value = eitherSuccess(data = expected)) }

            viewModel.detailUiState.test {
                viewModel.getDetail(detailArgs = movieDetailsArgs)

                assertTrue(awaitItem() is Idle)
                assertTrue(awaitItem() is Loading)
                with(receiver = awaitItem()) {
                    assertTrue(this is Data)
                    assertEquals(expected, detailModel)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getDetail is called unsuccessfully THEN returns an Empty state`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            coEvery { getDetailUseCase.safePrepare(input = movieDetailsArgs) } returns fakeFlowDomainError

            viewModel.detailUiState.test {
                viewModel.getDetail(detailArgs = movieDetailsArgs)

                assertTrue(awaitItem() is Idle)
                assertTrue(awaitItem() is Loading)
                assertTrue(awaitItem() is Empty)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getFavoriteStatus is called THEN returns a Data state with the corresponding favorite status`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            val expected = true
            coEvery { getFavoriteStatusUseCase.safePrepare(input = movieDetailsArgs) } returns flow { emit(value = expected) }

            viewModel.detailUiState.test {
                viewModel.getFavoriteStatus(detailArgs = movieDetailsArgs)

                assertTrue(awaitItem() is Idle)
                with(receiver = awaitItem()) {
                    assertTrue(this is Data)
                    assertEquals(expected, isFavorite)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    //    @Test
    //    fun `WHEN setFavoriteStatus is called successfully THEN returns the Data state with the corresponding favorite status`() = runTest
    // {
    //        with(receiver = safeFlowUseCaseDelegate) {
    //            val expected = true
    //            coEvery { setFavoriteStatusUseCase.safePrepare(input = movieDetailsArgs) } returns
    //                flow { emit(value = eitherSuccess(data = expected)) }
    //
    //            viewModel.detailUiState.test {
    //                viewModel.setFavoriteStatus()
    //
    //                assertTrue(awaitItem() is Idle)
    //                with(receiver = awaitItem()) {
    //                    assertTrue(this is Data)
    //                    assertEquals(expected, isFavorite)
    //                }
    //                cancelAndConsumeRemainingEvents()
    //            }
    //        }
    //    }

    //    @Test
    //    fun `WHEN setFavoriteStatus is called unsuccessfully THEN returns the Data state with the corresponding error`() = runTest {
    //        coEvery { savedState.toRoute<DetailArgs>(typeMap = DetailArgs.typeMap) } returns mockk(relaxed = true)
    //
    //        with(receiver = safeFlowUseCaseDelegate) {
    //            coEvery { setFavoriteStatusUseCase.safePrepare(input = movieDetailsArgs) } returns fakeFlowDomainError
    //
    //            viewModel.detailUiState.test {
    //                viewModel.setFavoriteStatus()
    //
    //                assertTrue(awaitItem() is Idle)
    //                assertTrue(awaitItem() is Empty)
    //                cancelAndConsumeRemainingEvents()
    //            }
    //        }
    //    }

    // endregion

    // region Helper methods
    private fun getExpectedFakeData(): DetailModel =
        when (searchType) {
            MOVIE -> fakeMovieDetailModel
            TV -> fakeTVDetailModel
        }
    // endregion
}
