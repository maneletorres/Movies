package com.manishsputnikcorporation.movies.domain.usecase.base.flow

import androidx.annotation.CheckResult
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<T, R>(protected open val dispatcherProvider: DispatcherProvider) {

    protected open fun dispatcher(): CoroutineContext = dispatcherProvider.io()

    @CheckResult fun prepare(input: T) = prepareFlow(input).flowOn(dispatcher())

    /** FlowUseCase returns a [Flow] that will be executed in the specified [CoroutineContext] being [Dispatchers.IO] by default. */
    protected abstract fun prepareFlow(input: T): Flow<R>
}
