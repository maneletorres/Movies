package com.manishsputnikcorporation.movies.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDetailModel
import com.manishsputnikcorporation.movies.utils.fakeFlowDomainError
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.movieDetailsArgs
import com.manishsputnikcorporation.movies.utils.tvDetailsArgs
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailViewModelTest {

    // region Setup
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
                analyticsManager = analyticsManager,
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
    fun `WHEN getMovieDetail is called successfully with a MOVIE type THEN updates the state with the movie detail data`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            coEvery { getDetailUseCase.safePrepare(input = movieDetailsArgs) } returns
                flow { emit(value = eitherSuccess(data = fakeMovieDetailModel)) }

            viewModel.detailUiState.test {
                viewModel.getMovieDetail(detailArgs = movieDetailsArgs)

                awaitItem().assertInitialState()
                with(receiver = awaitItem()) { assertEquals(false to true, isIdle to isLoading) }
                with(receiver = awaitItem()) { assertEquals(false to fakeMovieDetailModel, isLoading to detailData.detailModel) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getMovieDetail is called successfully with a TV type THEN updates the state with the TV detail data`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getDetailUseCase.safePrepare(input = tvDetailsArgs) } returns
                flow { emit(value = eitherSuccess(data = fakeTVDetailModel)) }

            viewModel.detailUiState.test {
                viewModel.getMovieDetail(detailArgs = tvDetailsArgs)

                awaitItem().assertInitialState()
                with(receiver = awaitItem()) { assertEquals(false to true, isIdle to isLoading) }
                with(receiver = awaitItem()) { assertEquals(false to fakeTVDetailModel, isLoading to detailData.detailModel) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getMovieDetail is called unsuccessfully with a MOVIE type THEN updates the state with isFailed true`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getDetailUseCase.safePrepare(input = movieDetailsArgs) } returns fakeFlowDomainError

            viewModel.detailUiState.test {
                viewModel.getMovieDetail(detailArgs = movieDetailsArgs)

                awaitItem().assertInitialState()
                with(receiver = awaitItem()) { assertEquals(false to true, isIdle to isLoading) }
                with(receiver = awaitItem()) { assertEquals(false to true, isLoading to isFailed) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getMovieDetail is called unsuccessfully with a TV type THEN updates the state with isFailed true`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getDetailUseCase.safePrepare(input = tvDetailsArgs) } returns fakeFlowDomainError

            viewModel.detailUiState.test {
                viewModel.getMovieDetail(detailArgs = tvDetailsArgs)

                awaitItem().assertInitialState()
                with(receiver = awaitItem()) { assertEquals(false to true, isIdle to isLoading) }
                with(receiver = awaitItem()) { assertEquals(false to true, isLoading to isFailed) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `WHEN getMovieFavoriteStatus is called with a MOVIE type THEN updates the state with the corresponding favorite status`() =
        runTest {
            with(receiver = safeFlowUseCaseDelegate) {
                every { getFavoriteStatusUseCase.safePrepare(input = movieDetailsArgs) } returns flow { emit(value = true) }

                viewModel.detailUiState.test {
                    viewModel.getMovieFavoriteStatus(detailArgs = movieDetailsArgs)

                    awaitItem().assertInitialState()
                    assertEquals(true, awaitItem().detailData.isFavorite)
                    cancelAndConsumeRemainingEvents()
                }
            }
        }

    @Test
    fun `WHEN getMovieFavoriteStatus is called with a TV type THEN updates the state with the corresponding favorite status`() = runTest {
        with(receiver = safeFlowUseCaseDelegate) {
            every { getFavoriteStatusUseCase.safePrepare(input = tvDetailsArgs) } returns flow { emit(value = true) }

            viewModel.detailUiState.test {
                viewModel.getMovieFavoriteStatus(detailArgs = tvDetailsArgs)

                awaitItem().assertInitialState()
                assertEquals(true, awaitItem().detailData.isFavorite)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    // TODO: infern hell
    //    @Test
    //    fun `WHEN setMovieFavoriteStatus is called successfully with a MOVIE type THEN updates the state with the corresponding favorite
    // status`() = runTest {
    //        with(receiver = safeFlowUseCaseDelegate) {
    //            every { setFavoriteStatusUseCase.safePrepare(input = movieDetailsArgs) } returns flow { emit(value = eitherSuccess(true))
    // }
    //
    //            viewModel.detailUiState.test {
    //                viewModel.setFavoriteStatus()
    //
    //                awaitItem().assertInitialState()
    //                assertEquals(true, awaitItem().detailData.isFavorite)
    //                cancelAndConsumeRemainingEvents()
    //            }
    //        }
    //    }
    //
    //    @Test
    //    fun `WHEN setMovieFavoriteStatus is called successfully with a TV type THEN updates the state with the corresponding favorite
    // status`() = runTest {
    //        with(receiver = safeFlowUseCaseDelegate) {
    //            every { setFavoriteStatusUseCase.safePrepare(input = tvDetailsArgs) } returns flow { emit(value = eitherSuccess(true)) }
    //
    //            viewModel.detailUiState.test {
    //                viewModel.setFavoriteStatus()
    //
    //                awaitItem().assertInitialState()
    //                assertEquals(true, awaitItem().detailData.isFavorite)
    //                cancelAndConsumeRemainingEvents()
    //            }
    //        }
    //    }
    //
    //    @Test
    //    fun `WHEN setMovieFavoriteStatus is called unsuccessfully with a MOVIE type THEN updates the state with the corresponding favorite
    // status`() = runTest {
    //        coEvery { savedState.toRoute<DetailArgs>(typeMap = DetailArgs.typeMap) } returns mockk(relaxed = true)
    //
    //        with(receiver = safeFlowUseCaseDelegate) {
    //            every { setFavoriteStatusUseCase.safePrepare(input = movieDetailsArgs) } returns fakeFlowDomainError
    //
    //            viewModel.detailUiState.test {
    //                viewModel.setFavoriteStatus()
    //
    //                awaitItem().assertInitialState()
    //                cancelAndConsumeRemainingEvents()
    //            }
    //        }
    //    }
    //
    //    @Test
    //    fun `WHEN setMovieFavoriteStatus is called unsuccessfully with a TV type THEN updates the state with the corresponding favorite
    // status`() = runTest {
    //        with(receiver = safeFlowUseCaseDelegate) {
    //            every { setFavoriteStatusUseCase.safePrepare(input = tvDetailsArgs) } returns fakeFlowDomainError
    //
    //            viewModel.detailUiState.test {
    //                viewModel.setFavoriteStatus()
    //
    //                awaitItem().assertInitialState()
    //                cancelAndConsumeRemainingEvents()
    //            }
    //        }
    //    }

    // endregion

    // region Helper methods
    private fun DetailUiState.assertInitialState() {
        assertEquals(true, isIdle)
        assertEquals(false, isLoading)
        assertEquals(null, detailData.detailModel)
        assertEquals(false, detailData.isFavorite)
        assertEquals(false, isFailed)
    }
    // endregion
}
