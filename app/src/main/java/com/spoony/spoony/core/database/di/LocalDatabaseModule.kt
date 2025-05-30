package com.spoony.spoony.core.database.di

import android.content.Context
import androidx.room.Room
import com.spoony.spoony.core.database.ExploreRecentSearchDatabase
import com.spoony.spoony.core.database.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Singleton
    @Provides
    fun providesDataBase(
        @ApplicationContext context: Context
    ): SearchDatabase =
        Room.databaseBuilder(context, SearchDatabase::class.java, "search-database").build()

    @Singleton
    @Provides
    fun providesDao(
        searchDatabase: SearchDatabase
    ) = searchDatabase.SearchDao()

    @Singleton
    @Provides
    fun providesExploreRecentSearchDataBase(
        @ApplicationContext context: Context
    ): ExploreRecentSearchDatabase =
        Room.databaseBuilder(context, ExploreRecentSearchDatabase::class.java, "explore-recent-search-database").build()

    @Singleton
    @Provides
    fun providesExploreRecentSearchDao(
        exploreRecentSearchDatabase: ExploreRecentSearchDatabase
    ) = exploreRecentSearchDatabase.ExploreRecentSearchDao()
}
