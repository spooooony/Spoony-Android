package com.spoony.spoony.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.spoony.spoony.data.datasource.local.SpoonLocalDataSource
import com.spoony.spoony.data.datasourceimpl.local.SpoonLocalDataSourceImpl
import com.spoony.spoony.data.repositoryimpl.SpoonLocalRepositoryImpl
import com.spoony.spoony.domain.repository.SpoonLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SPOON_DRAW_PREFERENCES = "spoon_draw_preferences"
private val Context.spoonDataStore: DataStore<Preferences> by preferencesDataStore(name = SPOON_DRAW_PREFERENCES)

@Module
@InstallIn(SingletonComponent::class)
object SpoonDataStoreModule {
    @Provides
    @Singleton
    fun provideSpoonLocalDataSource(
        @ApplicationContext context: Context
    ): SpoonLocalDataSource = SpoonLocalDataSourceImpl(context.spoonDataStore)

    @Provides
    @Singleton
    fun provideSpoonLocalRepository(
        dataSource: SpoonLocalDataSource
    ): SpoonLocalRepository = SpoonLocalRepositoryImpl(dataSource)
}
