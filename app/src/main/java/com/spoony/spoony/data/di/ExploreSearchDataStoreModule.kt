package com.spoony.spoony.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.spoony.spoony.data.datasource.local.RecentSearchDataSource
import com.spoony.spoony.data.datasourceimpl.local.RecentSearchDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreSearchDataStoreModule {

    @Provides
    @Singleton
    fun provideRecentSearchDataSource(
        dataStore: DataStore<Preferences>
    ): RecentSearchDataSource = RecentSearchDataSourceImpl(dataStore)
}
