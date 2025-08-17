package com.manishsputnikcorporation.movies.domain.error

import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber

interface FlowUseCaseDelegate {
    val globalErrorManager: GlobalErrorManager

    fun <T, R> FlowUseCase<T, R>.safePrepare(
        input: T,
        onGenericError: (suspend () -> Unit)? = null,
        onUnauthorized: (suspend () -> Unit)? = null,
        onNetworkAvailable: (suspend () -> Unit)? = null,
        dismissAction: (() -> Unit)? = null,
    ): Flow<R> =
        prepare(input).catch {
            Timber.e(this@safePrepare.javaClass.simpleName, it.toString())
            globalErrorManager.emitError(it, onGenericError, onUnauthorized, onNetworkAvailable, dismissAction)
        }

    class Default @Inject constructor(override val globalErrorManager: GlobalErrorManager) : FlowUseCaseDelegate
}
