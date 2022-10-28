package com.maneletorres.simpletemplate.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.error.SafeFlowUseCaseDelegate
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.domain.usecase.home.GetMoviesUseCase
import com.maneletorres.simpletemplate.ui.home.MoviesHomeViewModel.MoviesHomeUiState.*
import com.maneletorres.simpletemplate.utils.Either
import com.maneletorres.simpletemplate.utils.onFailure
import com.maneletorres.simpletemplate.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MoviesHomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val safeFlowUseCase: SafeFlowUseCaseDelegate
) : ViewModel(), SafeFlowUseCaseDelegate by safeFlowUseCase {

    private val _moviesHomeUiState: MutableStateFlow<MoviesHomeUiState> = MutableStateFlow(Idle)
    val moviesHomeUIState: StateFlow<MoviesHomeUiState> = _moviesHomeUiState.asStateFlow()

    private val _event: Channel<Event> = Channel()
    val event: Flow<Event> = _event.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        getMoviesUseCase
            .safePrepare<Unit, Either<List<MovieModel>, DomainError>>()
            .onStart { _moviesHomeUiState.value = Loading }
            .onEach {
                it.onSuccess { movieModelList: List<MovieModel> ->
                    _moviesHomeUiState.value = Data(movieModelList)
                }.onFailure {
                    _moviesHomeUiState.value = Error
                }
            }
            .launchIn(viewModelScope)
    }

    // region States
    sealed interface MoviesHomeUiState {
        object Idle : MoviesHomeUiState
        object Loading : MoviesHomeUiState
        data class Data(val movieList: List<MovieModel>) : MoviesHomeUiState
        object Error : MoviesHomeUiState
    }

    sealed interface Event {
        // TODO: implement if it's needed
    }
    // endregion
}