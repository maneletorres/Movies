package com.maneletorres.simpletemplate.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.error.SafeFlowUseCaseDelegate
import com.maneletorres.simpletemplate.domain.model.IMDBMovieModel
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.domain.usecase.home.GetMoviesUseCase
import com.maneletorres.simpletemplate.domain.usecase.home.GetMoviesUseCase2
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
    private val getMoviesUseCase2: GetMoviesUseCase2, // TODO: to delete on of these use cases.
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
            .safePrepare<Unit, Either<List<IMDBMovieModel>, DomainError>>()
            .onStart { _moviesHomeUiState.value = Loading }
            .onEach {
                it.onSuccess { imdbMovieModelList: List<IMDBMovieModel> ->
                    _moviesHomeUiState.value = Data(imdbMovieModelList)
                }.onFailure {
                    _moviesHomeUiState.value = Error
                }
            }
            .launchIn(viewModelScope)
    }

    // TODO: doesn't work!!
    private fun loadData2() {
        getMoviesUseCase2
            .safePrepare<Unit, Either<List<MovieModel>, DomainError>>()
            .onStart { Loading }
            .onEach {
                it.onSuccess { imdbMovieModelList: List<MovieModel> ->
                    // _state.value = Data(imdbMovieModelList)
                }.onFailure {
                    _moviesHomeUiState.value = Error
                }
            }
            .launchIn(viewModelScope)
    }

    private fun navigateToMovie(movieId: String, view: View) {
        val direction = MoviesHomeFragmentDirections.actionMoviesHomeFragmentToMovieDetailFragment(movieId)
        view.findNavController().navigate(direction)
    }

    // region States
    sealed interface MoviesHomeUiState {
        object Idle : MoviesHomeUiState
        object Loading : MoviesHomeUiState
        data class Data(val movieList: List<IMDBMovieModel>) : MoviesHomeUiState
        object Error : MoviesHomeUiState
    }

    sealed interface Event {

    }
    // endregion
}