package com.manishsputnikcorporation.movies.di

// import androidx.test.espresso.core.internal.deps.dagger.Binds
// import androidx.test.espresso.core.internal.deps.dagger.Module
// import androidx.test.espresso.core.internal.deps.dagger.Provides
// import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
// import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSourceImpl
// import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
// import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSourceImpl
// import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
// import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSourceImpl
// import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSource
// import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSourceImpl
// import com.manishsputnikcorporation.movies.di.annotations.ApiUrl
// import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceExecutor
// import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
// import com.manishsputnikcorporation.movies.network.util.TEST_API_BASE_URL
// import dagger.hilt.InstallIn
// import dagger.hilt.components.SingletonComponent
// import dagger.hilt.testing.TestInstallIn
// import javax.inject.Singleton
//
// @Module
// @InstallIn(SingletonComponent::class)
// object FakeRetrofitModule {
//
//    @Provides
//    @Singleton
//    @ApiUrl
//    fun provideBaseUrl() = TEST_API_BASE_URL
// }
//
// @Module
// @TestInstallIn(components = [SingletonComponent::class], replaces = [FakeRetrofitModule::class])
// abstract class FakeDataSourceModule {
//
//    // TODO: ¿to mock this section?
//    @Binds
//    @Singleton
//    abstract fun bindServiceExecutor(retrofitServicesExecutor: RetrofitServiceExecutor): ServiceExecutor
//
//    @Binds @Singleton
//    abstract fun bindMovieLocalDataSource(movieLocalDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource
//
//    @Binds
//    @Singleton
//    abstract fun bindMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource
//
//    @Binds @Singleton
//    abstract fun bindTvLocalDataSource(tvLocalDataSource: TVLocalDataSourceImpl): TVLocalDataSource
//
//    @Binds @Singleton
//    abstract fun bindTVRemoteDataSource(tvRemoteDataSource: TVRemoteDataSourceImpl): TVRemoteDataSource
// }
