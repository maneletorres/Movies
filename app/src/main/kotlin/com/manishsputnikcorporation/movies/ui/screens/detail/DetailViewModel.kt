package com.manishsputnikcorporation.movies.ui.screens.detail

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.model.DetailModel
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetDetailUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.GetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.domain.usecase.detail.SetFavoriteStatusUseCase
import com.manishsputnikcorporation.movies.ui.navigation.DetailArgs
import com.manishsputnikcorporation.movies.utils.enums.Type
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
class DetailViewModel
@Inject
constructor(
    private val getDetailUseCase: GetDetailUseCase,
    private val getFavoriteStatusUseCase: GetFavoriteStatusUseCase,
    private val setFavoriteStatusUseCase: SetFavoriteStatusUseCase,
    private val safeFlowUseCase: FlowUseCaseDelegate,
    val savedStateHandle: SavedStateHandle,
) : ViewModel(), FlowUseCaseDelegate by safeFlowUseCase {

    private val _detailUiState = MutableStateFlow<DetailUiState>(value = DetailUiState.Idle)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    fun loadData() {
        val detailArgs: DetailArgs = DetailArgs.from(savedStateHandle)
        getDetail(detailArgs)
        getFavoriteStatus(detailArgs)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getDetail(detailArgs: DetailArgs) {
        getDetailUseCase
            .safePrepare(input = detailArgs)
            .onStart { _detailUiState.update { DetailUiState.Loading } }
            .onEach {
                it.onSuccess { detailModel: DetailModel ->
                        _detailUiState.update { currentState ->
                            if (currentState is DetailUiState.Data) currentState.copy(detailModel = detailModel)
                            else DetailUiState.Data(detailModel = detailModel, searchType = detailArgs.type)
                        }
                    }
                    .onFailure { error ->
                        _detailUiState.update { DetailUiState.Empty(errorMessage = error.message, searchType = detailArgs.type) }
                    }
            }
            .launchIn(viewModelScope)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun getFavoriteStatus(detailArgs: DetailArgs) {
        getFavoriteStatusUseCase
            .safePrepare(input = detailArgs)
            .onEach { favoriteStatus -> updateFavoriteStatus(favoriteStatus = favoriteStatus, searchType = detailArgs.type) }
            .launchIn(viewModelScope)
    }

    fun setFavoriteStatus() {
        val detailArgs = DetailArgs.from(savedStateHandle)
        setFavoriteStatusUseCase
            .safePrepare(input = detailArgs)
            .onEach {
                it.onSuccess { favoriteStatus -> updateFavoriteStatus(favoriteStatus = favoriteStatus, searchType = detailArgs.type) }
                    .onFailure { error ->
                        _detailUiState.update { DetailUiState.Empty(errorMessage = error.message, searchType = detailArgs.type) }
                    }
            }
            .launchIn(viewModelScope)
    }

    // region Helper methods
    private fun updateFavoriteStatus(favoriteStatus: Boolean, searchType: Type) {
        _detailUiState.update { currentState ->
            if (currentState is DetailUiState.Data) currentState.copy(isFavorite = favoriteStatus)
            else DetailUiState.Data(isFavorite = favoriteStatus, searchType = searchType)
        }
    }

    // endregion

    // region State & Events
    sealed interface DetailUiState {

        data object Idle : DetailUiState

        data object Loading : DetailUiState

        data class Data(val detailModel: DetailModel? = null, val isFavorite: Boolean = false, val searchType: Type) : DetailUiState

        data class Empty(val errorMessage: String, val searchType: Type) : DetailUiState
    }

    sealed interface DetailEvent {
        object Error : DetailEvent
    }
    // endregion
}
