package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.base.Model
import com.manishsputnikcorporation.movies.domain.usecase.home.GetDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.GetFavoriteDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.LoadDataUseCase
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.HomeEvent.Error
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.HomeUiState.Data
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.HomeUiState.Idle
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.HomeUiState.Loading
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val authManager: AuthManager,
    private val loadDataUseCase: LoadDataUseCase,
    private val getDataUseCase: GetDataUseCase,
    private val getFavoriteDataUseCase: GetFavoriteDataUseCase,
    private val safeFlowUseCase: FlowUseCaseDelegate,
) : ViewModel(), FlowUseCaseDelegate by safeFlowUseCase {

    //  val moviesHomeUIState: StateFlow<MoviesHomeUiState> =
    //      moviesRepository.movies
    //          .map { movieModelList -> Data(movieModelList) }
    //          .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

    private val _homeUiState = MutableStateFlow<HomeUiState>(value = Idle)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _homeEvent: Channel<HomeEvent> = Channel()
    val homeEvent: Flow<HomeEvent> = _homeEvent.receiveAsFlow()

    fun loadData(type: Type = Type.MOVIE) {
        loadDataUseCase
            .safePrepare(input = type)
            .onStart { _homeUiState.update { Loading } }
            .onCompletion { throwable -> if (throwable == null) getData(type) }
            .catch { _homeEvent.send(element = Error(searchType = type.name.lowercase())) }
            .launchIn(viewModelScope)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getData(type: Type) {
        getDataUseCase
            .safePrepare(input = type)
            .onEach { mediaModelList ->
                _homeUiState.update { currentState ->
                    val user = authManager.getCurrentUser()
                    if (currentState is Data) currentState.copy(mediaModelList = mediaModelList, user = user)
                    else Data(mediaModelList = mediaModelList, user = user)
                }
            }
            .catch { _homeEvent.send(element = Error(searchType = type.name.lowercase())) }
            .launchIn(viewModelScope)
    }

    fun getFavorites(type: Type) {
        getFavoriteDataUseCase
            .safePrepare(input = type)
            .onEach { mediaModelList ->
                _homeUiState.update { currentState ->
                    if (currentState is Data) currentState.copy(mediaModelList = mediaModelList) else Data(mediaModelList = mediaModelList)
                }
            }
            .catch { _homeEvent.send(element = Error(searchType = type.name.lowercase())) }
            .launchIn(viewModelScope)
    }

    // region State & Events
    sealed interface HomeUiState {

        data object Idle : HomeUiState

        data object Loading : HomeUiState

        data class Data(val mediaModelList: List<Model>, val user: FirebaseUser? = null) : HomeUiState
    }

    sealed interface HomeEvent {
        data class Error(val searchType: String) : HomeEvent
    }
    // endregion
}
