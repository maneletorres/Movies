package com.maneletorres.simpletemplate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maneletorres.simpletemplate.domain.error.SafeFlowUseCaseDelegate
import com.maneletorres.simpletemplate.domain.model.MovieDetailModel
import com.maneletorres.simpletemplate.domain.usecase.detail.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.maneletorres.simpletemplate.ui.detail.MovieDetailViewModel.MovieDetailUiState.*
import com.maneletorres.simpletemplate.utils.onFailure
import com.maneletorres.simpletemplate.utils.onSuccess

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