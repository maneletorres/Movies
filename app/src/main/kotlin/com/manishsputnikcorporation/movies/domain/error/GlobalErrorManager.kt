package com.manishsputnikcorporation.movies.domain.error

import com.manishsputnikcorporation.movies.domain.error.model.GlobalErrorHandlerModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class GlobalErrorManager @Inject constructor(private val defaultErrorMapper: GlobalErrorMapper) {

  private val _event: MutableSharedFlow<GlobalErrorHandlerModel> = MutableSharedFlow()
  val event: SharedFlow<GlobalErrorHandlerModel> = _event.asSharedFlow()

  suspend fun emitError(
      throwable: Throwable,
      onGenericError: (suspend () -> Unit)? = null,
      onUnauthorized: (suspend () -> Unit)? = null,
      onNetworkAvailable: (suspend () -> Unit)? = null,
      dismissAction: (() -> Unit)? = null
  ) {
    _event.emit(
        GlobalErrorHandlerModel(
            defaultErrorMapper.map(throwable),
            onGenericError,
            onUnauthorized,
            onNetworkAvailable,
            dismissAction))
  }
}
