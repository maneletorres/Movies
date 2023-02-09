package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.usecase.home.GetMoviesUseCase
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.*
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@HiltViewModel
class MoviesHomeViewModel
@Inject
constructor(
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
              }
              .onFailure { _moviesHomeUiState.value = Error }
        }
        .launchIn(viewModelScope)
  }

  // region States
  sealed class MoviesHomeUiState {
    object Idle : MoviesHomeUiState()
    object Loading : MoviesHomeUiState()
    data class Data(val movieList: List<MovieModel>) : MoviesHomeUiState()
    object Error : MoviesHomeUiState()
  }

  sealed interface Event {
    // TODO: implement if it's needed
  }
  // endregion
}
