package com.manishsputnikcorporation.movies.ui.screens.detail

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.DetailModel
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.ui.navigation.DetailArgs
import com.manishsputnikcorporation.movies.ui.navigation.MovieDetail
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val analyticsManager: AnalyticsManager,
    private val getDetailUseCase: GetDetailUseCase,
    private val getFavoriteStatusUseCase: GetFavoriteStatusUseCase,
    private val setFavoriteStatusUseCase: SetFavoriteStatusUseCase,
    private val safeFlowUseCase: FlowUseCaseDelegate,
    val savedStateHandle: SavedStateHandle,
) : ViewModel(), FlowUseCaseDelegate by safeFlowUseCase {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = MovieDetail.route)

    fun loadData() {
        val detailArgs: DetailArgs = DetailArgs.from(savedStateHandle)
        getMovieDetail(detailArgs)
        getMovieFavoriteStatus(detailArgs)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getMovieDetail(detailArgs: DetailArgs) {
        getDetailUseCase
            .safePrepare(input = detailArgs)
            .onStart { _detailUiState.update { state -> state.copy(isIdle = false, isLoading = true) } }
            .onEach {
                it.onSuccess { detailModel: DetailModel ->
                        _detailUiState.update { state ->
                            state.copy(isLoading = false, detailData = state.detailData.copy(detailModel = detailModel))
                        }
                    }
                    .onFailure { _detailUiState.update { state -> state.copy(isLoading = false, isFailed = true) } }
            }
            .launchIn(viewModelScope)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getMovieFavoriteStatus(detailArgs: DetailArgs) {
        getFavoriteStatusUseCase
            .safePrepare(input = detailArgs)
            .onEach { favoriteStatus -> updateFavoriteStatus(favoriteStatus) }
            .launchIn(viewModelScope)
    }

    fun setFavoriteStatus() {
        setFavoriteStatusUseCase
            .safePrepare(input = DetailArgs.from(savedStateHandle))
            .onEach {
                it.onSuccess { favoriteStatus -> updateFavoriteStatus(favoriteStatus) }
                    .onFailure {
                        // Nothing to do
                    }
            }
            .launchIn(viewModelScope)
    }

    // region Helper methods
    private fun updateFavoriteStatus(favoriteStatus: Boolean) {
        _detailUiState.update { state -> state.copy(detailData = state.detailData.copy(isFavorite = favoriteStatus)) }
    }

    // endregion

    // region State & Events
    data class DetailUiState(
        val isIdle: Boolean = true,
        val isLoading: Boolean = false,
        val detailData: DetailData = DetailData(),
        val isFailed: Boolean = false,
    ) {

        data class DetailData(val detailModel: DetailModel? = null, val isFavorite: Boolean = false)
    }
    // endregion
}
