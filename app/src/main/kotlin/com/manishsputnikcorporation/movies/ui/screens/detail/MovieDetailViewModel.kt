package com.manishsputnikcorporation.movies.ui.screens.detail

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetMovieFavoriteStatusUseCase
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
class MovieDetailViewModel
@Inject
constructor(
    private val analyticsManager: AnalyticsManager,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieFavoriteStatusUseCase: GetMovieFavoriteStatusUseCase,
    private val setMovieFavoriteStatusUseCase: SetMovieFavoriteStatusUseCase,
    private val safeFlowUseCase: FlowUseCaseDelegate,
) : ViewModel(), FlowUseCaseDelegate by safeFlowUseCase {

    private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState())
    val movieDetailUiState: StateFlow<MovieDetailUiState> = _movieDetailUiState

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = MovieDetail.route)

    fun loadData(id: Int) {
        getMovieDetail(id = id)
        getMovieFavoriteStatus(id = id)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getMovieDetail(id: Int) {
        getMovieDetailUseCase
            .safePrepare(input = id)
            .onStart { _movieDetailUiState.update { state -> state.copy(isIdle = false, isLoading = true) } }
            .onEach {
                it.onSuccess { movieDetailModel: MovieDetailModel ->
                        _movieDetailUiState.update { state ->
                            state.copy(isLoading = false, movieDetailData = state.movieDetailData.copy(movieDetailModel = movieDetailModel))
                        }
                    }
                    .onFailure { _movieDetailUiState.update { state -> state.copy(isLoading = false, isFailed = true) } }
            }
            .launchIn(viewModelScope)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getMovieFavoriteStatus(id: Int) {
        getMovieFavoriteStatusUseCase
            .safePrepare(input = id)
            .onEach { favoriteStatus -> updateFavoriteStatus(favoriteStatus) }
            .launchIn(viewModelScope)
    }

    fun setMovieFavoriteStatus(id: Int) {
        setMovieFavoriteStatusUseCase
            .safePrepare(input = id)
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
        _movieDetailUiState.update { state -> state.copy(movieDetailData = state.movieDetailData.copy(isFavorite = favoriteStatus)) }
    }

    // endregion

    // region State & Events
    data class MovieDetailUiState(
        val isIdle: Boolean = true,
        val isLoading: Boolean = false,
        val movieDetailData: MovieDetailData = MovieDetailData(),
        val isFailed: Boolean = false,
    ) {

        data class MovieDetailData(val movieDetailModel: MovieDetailModel? = null, val isFavorite: Boolean = false)
    }
    // endregion
}
