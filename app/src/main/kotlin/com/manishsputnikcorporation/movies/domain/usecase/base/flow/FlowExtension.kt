package com.manishsputnikcorporation.movies.domain.usecase.base.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

@JvmName("flattenList") fun <T> Flow<List<T>>.flatten(): Flow<List<T>> = flow { emit(toList().flatten()) }

fun <T> Flow<T>.flatten(): Flow<List<T>> = flow { emit(toList()) }

typealias FlowHandler<T> = suspend FlowCollector<T>.() -> Unit
