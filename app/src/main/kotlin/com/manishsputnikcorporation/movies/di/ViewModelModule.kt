package com.manishsputnikcorporation.movies.di

import com.manishsputnikcorporation.movies.data.repository.imdb.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.data.repository.imdb.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.domain.error.*
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DefaultDispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.network.model.tmdb.movie.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.model.tmdb.movie.MovieDtoMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

  @Binds
  @ViewModelScoped
  abstract fun bindSafeFlowUseCaseDelegate(
      safeFlowUseCaseDelegate: SafeFlowUseCaseDelegate.Default
  ): SafeFlowUseCaseDelegate

  @Binds
  @ViewModelScoped
  abstract fun bindGlobalErrorManager(
      globalErrorManager: GlobalErrorManagerImpl
  ): GlobalErrorManager

  @Binds
  @ViewModelScoped
  abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

  @Binds
  @ViewModelScoped
  abstract fun bindMovieDetailRepository(
      movieDetailRepository: MovieDetailRepositoryImpl
  ): MovieDetailRepository

  @Binds
  @ViewModelScoped
  abstract fun bindGlobalErrorMapper(globalErrorMapper: GlobalErrorMapperImpl): GlobalErrorMapper

  @Binds
  @ViewModelScoped
  abstract fun bindDispatcherProvider(
      dispatcherProvider: DefaultDispatcherProvider
  ): DispatcherProvider
}

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

  @Provides @ViewModelScoped fun provideMovieDtoMapper(): MovieDtoMapper = MovieDtoMapper()

  @Provides
  @ViewModelScoped
  fun provideMovieDetailDtoMapper(): MovieDetailDtoMapper = MovieDetailDtoMapper()
}
