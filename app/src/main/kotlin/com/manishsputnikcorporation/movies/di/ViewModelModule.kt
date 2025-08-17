package com.manishsputnikcorporation.movies.di

import android.content.Context
import com.manishsputnikcorporation.movies.data.repository.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.data.repository.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.domain.error.*
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DefaultDispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.network.model.MediaItemDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.TvShowDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManagerImpl
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds @ViewModelScoped abstract fun bindFlowUseCaseDelegate(flowUseCaseDelegate: FlowUseCaseDelegate.Default): FlowUseCaseDelegate

    @Binds @ViewModelScoped abstract fun bindGlobalErrorManager(globalErrorManager: GlobalErrorManagerImpl): GlobalErrorManager

    @Binds @ViewModelScoped abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

    @Binds @ViewModelScoped abstract fun bindMovieDetailRepository(movieDetailRepository: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds @ViewModelScoped abstract fun bindGlobalErrorMapper(globalErrorMapper: GlobalErrorMapperImpl): GlobalErrorMapper

    @Binds @ViewModelScoped abstract fun bindDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

    @Provides @ViewModelScoped fun provideMovieDtoMapper(): MediaItemDtoMapper = MediaItemDtoMapper()

    @Provides @ViewModelScoped fun provideMovieDetailDtoMapper(): MovieDetailDtoMapper = MovieDetailDtoMapper()

    @Provides @ViewModelScoped fun provideTvShowDetailDtoMapper(): TvShowDetailDtoMapper = TvShowDetailDtoMapper()
}

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseModule {
    @Provides
    @ViewModelScoped
    fun bindAnalyticsManager(@ApplicationContext context: Context): AnalyticsManager = AnalyticsManagerImpl(context)

    @Provides @ViewModelScoped fun bindAuthManager(@ApplicationContext context: Context): AuthManager = AuthManagerImpl(context)
}
