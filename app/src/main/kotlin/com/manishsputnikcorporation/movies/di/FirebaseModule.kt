package com.manishsputnikcorporation.movies.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManagerImpl
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides @Singleton fun provideAnalyticsManager(@ApplicationContext context: Context): AnalyticsManager = AnalyticsManagerImpl(context)

    @Provides @Singleton fun provideAuthManager(@ApplicationContext context: Context): AuthManager = AuthManagerImpl(context)

    @Provides @Singleton fun provideCredentialManager(@ApplicationContext context: Context) = CredentialManager.create(context)
}
