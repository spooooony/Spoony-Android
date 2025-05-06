package com.spoony.spoony.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.spoony.spoony.data.datasource.local.TokenDataSource
import com.spoony.spoony.data.datasourceimpl.local.TokenDataSourceImpl
import com.spoony.spoony.data.repositoryimpl.TokenRepositoryImpl
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TOKEN_PREFERENCES = "token_preferences"
private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_PREFERENCES)

@Module
@InstallIn(SingletonComponent::class)
object TokenDataStoreModule {
    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.tokenDataStore

    @Provides
    @Singleton
    fun provideTokenDataSource(
        dataStore: DataStore<Preferences>
    ): TokenDataSource = TokenDataSourceImpl(dataStore)

    @Provides
    @Singleton
    fun provideTokenRepository(
        dataSource: TokenDataSource
    ): TokenRepository = TokenRepositoryImpl(dataSource)
}
