package com.manishsputnikcorporation.movies.ui.screens.home

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.domain.usecase.home.GetDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.GetFavoriteDataUseCase
import com.manishsputnikcorporation.movies.domain.usecase.home.LoadDataUseCase
import com.manishsputnikcorporation.movies.ui.navigation.MoviesHome
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.Data
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.Idle
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.Loading
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesHomeViewModel
@Inject
constructor(
    private val analyticsManager: AnalyticsManager,
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

    private val _moviesHomeUiState = MutableStateFlow<MoviesHomeUiState>(Idle)
    val moviesHomeUiState: StateFlow<MoviesHomeUiState> = _moviesHomeUiState

    private val _event: Channel<Event> = Channel()
    val event: Flow<Event> = _event.receiveAsFlow()

    init {
        loadData()
        loadUserPhotoUrl()
    }

    @SuppressLint("ComposableNaming") @Composable fun trackScreen() = analyticsManager.trackScreen(screenName = MoviesHome.route)

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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getData(type: Type) {
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loadUserPhotoUrl() {
        viewModelScope.launch {
            _moviesHomeUiState.update {
                Data(movieList = listOf(), authManager.getCurrentUser()) // TODO: it.copy
            }
        }
    }

    // region State & Events
    sealed interface MoviesHomeUiState {

        data object Idle : MoviesHomeUiState

        data object Loading : MoviesHomeUiState

        data class Data(val movieList: List<MediaItemModel>, val user: FirebaseUser? = null) : MoviesHomeUiState
    }

    sealed interface Event {
        data class Error(val searchType: String) : Event
    }
    // endregion
}
