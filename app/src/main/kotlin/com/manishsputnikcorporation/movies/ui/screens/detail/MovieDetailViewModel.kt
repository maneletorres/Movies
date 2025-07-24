package com.manishsputnikcorporation.movies.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetMovieFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
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
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieFavoriteStatusUseCase: GetMovieFavoriteStatusUseCase,
    private val setMovieFavoriteStatusUseCase: SetMovieFavoriteStatusUseCase,
    private val safeFlowUseCase: FlowUseCaseDelegate,
) : ViewModel(), FlowUseCaseDelegate by safeFlowUseCase {

    private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState())
    val movieDetailUiState: StateFlow<MovieDetailUiState> = _movieDetailUiState

    fun loadData(id: String?) {
        id?.let {
            // TODO: to merge these two methods!
            getMovieDetail(it)
            getMovieFavoriteStatus(it)
        }
    }

    private fun getMovieDetail(id: String?) {
        id?.let {
            getMovieDetailUseCase
                .safePrepare(id)
                .onStart { _movieDetailUiState.update { state -> state.copy(isIdle = false, isLoading = true) } }
                .onEach {
                    it.onSuccess { movieDetailModel: MovieDetailModel ->
                            _movieDetailUiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    movieDetailData = state.movieDetailData.copy(movieDetailModel = movieDetailModel),
                                )
                            }
                        }
                        .onFailure { _movieDetailUiState.update { state -> state.copy(isLoading = false, isFailed = true) } }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getMovieFavoriteStatus(id: String) {
        getMovieFavoriteStatusUseCase
            .safePrepare(id)
            .onEach { favoriteStatus -> updateFavoriteStatus(favoriteStatus) }
            .launchIn(viewModelScope)
    }

    fun setMovieFavoriteStatus(id: String) {
        setMovieFavoriteStatusUseCase
            .safePrepare(id)
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
