package com.maneletorres.simpletemplate.di

import com.maneletorres.simpletemplate.domain.error.SafeFlowUseCaseDelegate
import com.maneletorres.simpletemplate.domain.usecase.base.dispatchers.DefaultDispatcherProvider
import com.maneletorres.simpletemplate.domain.usecase.base.dispatchers.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindSafeFlowUseCaseDelegateProvider(default: SafeFlowUseCaseDelegate.Default): SafeFlowUseCaseDelegate

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider

//    @Binds
//    fun bindGlobalErrorManagerProvider(defaultErrorMapper: GlobalErrorMapper): GlobalErrorManager =
//        GlobalErrorManager(defaultErrorMapper)
}