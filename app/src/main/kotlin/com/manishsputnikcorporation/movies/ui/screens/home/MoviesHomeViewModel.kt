package com.manishsputnikcorporation.movies.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.SafeFlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.home.GetNetworkMoviesUseCase
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
    moviesRepository: MoviesRepository,
    private val getNetworkMoviesUseCase: GetNetworkMoviesUseCase,
    private val safeFlowUseCase: SafeFlowUseCaseDelegate
) : ViewModel(), SafeFlowUseCaseDelegate by safeFlowUseCase {

  val moviesHomeUIState: StateFlow<MoviesHomeUiState> =
      moviesRepository.movies
          .map {
            it.filter { movieModel -> movieModel.type == Type.MOVIE }
            if (it.isEmpty()) Empty else Data(Type.MOVIE, it)
          }
          .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

  private val _event: Channel<Event> = Channel()
  val event: Flow<Event> = _event.receiveAsFlow()

  init {
    // loadDatabaseData()
    loadNetworkData()
  }

  //  private fun loadDatabaseData() {
  //    getDatabaseMoviesUseCase
  //        .safePrepare<Unit, List<MovieModel>>()
  //        .onStart { _moviesHomeUiState.value = Loading }
  //        .onEach { movieModelList: List<MovieModel> ->
  //          _moviesHomeUiState.value = Data(movieModelList)
  //        }
  //        .onEmpty {
  //            // TODO: to investigate
  //        }
  //        .launchIn(viewModelScope)
  //  }

  fun loadNetworkData(searchType: Type = Type.MOVIE) {
    getNetworkMoviesUseCase
        .safePrepare(searchType)
        .onEach {
          it.onSuccess {
                // TODO: nothing to do.
              }
              .onFailure {
                Log.e("", "") // TODO:
              }
        }
        .launchIn(viewModelScope)
  }

  // region States
  sealed class MoviesHomeUiState {
    object Empty : MoviesHomeUiState() // TODO: to delete?
    object Loading : MoviesHomeUiState()
    data class Data(val searchType: Type, val movieList: List<MovieModel>) : MoviesHomeUiState()
    object Error : MoviesHomeUiState()
  }

  sealed interface Event {
    // TODO: implement if it's needed
  }
  // endregion
}
