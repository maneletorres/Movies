package com.manishsputnikcorporation.movies.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetMovieUseCase
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState.*
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val safeFlowUseCase: SafeFlowUseCaseDelegate,
) : ViewModel(), SafeFlowUseCaseDelegate by safeFlowUseCase {

    private val _movieDetailUiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(Idle)
    val movieDetailUIState: StateFlow<MovieDetailUiState> = _movieDetailUiState.asStateFlow()

    fun loadMovieDetail(id: String?) {
        id?.let {
            getMovieUseCase
                .safePrepare(id)
                .onStart {
                    _movieDetailUiState.value = Loading
                }
                .onEach {
                    it.onSuccess { movieDetailModel: MovieDetailModel ->
                        _movieDetailUiState.value = Data(movieDetailModel)
                    }.onFailure {
                        _movieDetailUiState.value = Error
                    }
                }.launchIn(viewModelScope)
        }
    }

    sealed class MovieDetailUiState {
        object Idle : MovieDetailUiState()
        object Loading : MovieDetailUiState()
        data class Data(val movieDetail: MovieDetailModel) : MovieDetailUiState()
        object Error : MovieDetailUiState()
    }
}