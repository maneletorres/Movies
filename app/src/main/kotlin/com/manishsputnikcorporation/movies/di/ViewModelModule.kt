package com.manishsputnikcorporation.movies.di

import com.manishsputnikcorporation.movies.data.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.data.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.domain.error.*
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DefaultDispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

  @Binds
  abstract fun bindSafeFlowUseCaseDelegate(
      safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate.Default
  ): SafeFlowUseCaseDelegate

  @Binds
  abstract fun bindGlobalErrorManager(
      globalErrorManager: GlobalErrorManagerImpl
  ): GlobalErrorManager

  @Binds abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

  @Binds
  abstract fun bindMovieDetailRepository(
      movieDetailRepository: MovieDetailRepositoryImpl
  ): MovieDetailRepository

  @Binds
  abstract fun bindGlobalErrorMapper(globalErrorMapper: GlobalErrorMapperImpl): GlobalErrorMapper

  @Binds
  abstract fun bindDispatcherProvider(
      dispatcherProvider: DefaultDispatcherProvider
  ): DispatcherProvider
}

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

  @Provides fun provideMovieDtoMapper(): MovieDtoMapper = MovieDtoMapper()

  @Provides fun provideMovieDetailDtoMapper(): MovieDetailDtoMapper = MovieDetailDtoMapper()
}
