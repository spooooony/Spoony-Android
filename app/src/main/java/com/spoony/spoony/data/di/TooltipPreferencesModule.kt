package com.spoony.spoony.data.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.spoony.spoony.data.datasource.TooltipPreferencesDataSource
import com.spoony.spoony.data.repositoryimpl.TooltipPreferencesRepositoryImpl
import com.spoony.spoony.domain.repository.TooltipPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "tooltip_preferences")

@Module
@InstallIn(SingletonComponent::class)
object TooltipPreferencesModule {

    @Provides
    @Singleton
    fun provideTooltipPreferencesDataSource(
        @ApplicationContext context: Context
    ): TooltipPreferencesDataSource =
        TooltipPreferencesDataSource(context.dataStore)

    @Provides
    @Singleton
    fun provideTooltipPreferencesRepository(
        dataSource: TooltipPreferencesDataSource
    ): TooltipPreferencesRepository = TooltipPreferencesRepositoryImpl(dataSource)
}
