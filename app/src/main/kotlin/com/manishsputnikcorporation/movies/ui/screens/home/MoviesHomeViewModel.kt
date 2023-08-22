package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.domain.usecase.home.GetDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.GetFavoriteDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.LoadDataUseCase
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.*
import com.manishsputnikcorporation.movies.utils.enums.Type
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
    private val loadDataUseCase: LoadDataUseCase,
    private val getDataUseCase: GetDataUseCase,
    private val getFavoriteDataUseCase: GetFavoriteDataUseCase,
    private val safeFlowUseCase: FlowUseCaseDelegate
) : ViewModel(), FlowUseCaseDelegate by safeFlowUseCase {

  //  val moviesHomeUIState: StateFlow<MoviesHomeUiState> =
  //      moviesRepository.movies
  //          .map { movieModelList -> Data(movieModelList) }
  //          .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

  private val _moviesHomeUiState = MutableStateFlow<MoviesHomeUiState>(Idle)
  val moviesHomeUiState: StateFlow<MoviesHomeUiState> = _moviesHomeUiState

  private val _event: Channel<Event> = Channel()
  val event: Flow<Event> = _event.receiveAsFlow()

  init {
    loadData()
  }

  fun loadData(type: Type = Type.MOVIE) {
    loadDataUseCase
        .safePrepare(type)
        .onStart { _moviesHomeUiState.update { Loading } }
        .onEach {
          getData(type)
          it.onSuccess {
                // Nothing to do
              }
              .onFailure { _event.send(Event.Error(type.name.lowercase())) }
        }
        .launchIn(viewModelScope)
  }

  private fun getData(type: Type) {
    getDataUseCase
        .safePrepare(type)
        .onEach { movieModelList -> _moviesHomeUiState.update { Data(movieModelList) } }
        .launchIn(viewModelScope)
  }

  fun getFavorites(type: Type) {
    getFavoriteDataUseCase
        .safePrepare(type)
        .onEach { movieModelList -> _moviesHomeUiState.update { Data(movieModelList) } }
        .launchIn(viewModelScope)
  }

  // region State & Events
  sealed interface MoviesHomeUiState {

    data object Idle : MoviesHomeUiState
    data object Loading : MoviesHomeUiState
    data class Data(val movieList: List<MediaItemModel>) : MoviesHomeUiState
  }

  sealed interface Event {
    data class Error(val searchType: String) : Event
  }
  // endregion
}
