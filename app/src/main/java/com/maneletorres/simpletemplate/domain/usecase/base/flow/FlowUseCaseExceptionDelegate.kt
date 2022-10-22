package com.maneletorres.simpletemplate.domain.usecase.base.flow

import com.maneletorres.simpletemplate.domain.error.GlobalErrorMapper
import com.maneletorres.simpletemplate.domain.error.GlobalErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

interface FlowUseCaseExceptionDelegate {

    val globalErrorMapper: GlobalErrorMapper

    fun <T> Flow<T>.spreadUnauthorized(block: () -> T): Flow<T> = catchIfNot(GlobalErrorType.UNAUTHORIZED, block)

    fun <T> Flow<T>.catchIfNot(type: GlobalErrorType, block: () -> T): Flow<T> =
        catch { cause: Throwable -> if (globalErrorMapper.map(cause) != type) emit(block()) else throw cause }

    fun <T> Flow<T>.catchIf(type: GlobalErrorType, block: () -> T): Flow<T> =
        catch { cause: Throwable -> if (globalErrorMapper.map(cause) == type) emit(block()) else throw cause }
}