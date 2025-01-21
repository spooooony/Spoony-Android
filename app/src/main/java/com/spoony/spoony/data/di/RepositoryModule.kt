package com.spoony.spoony.data.di

import com.spoony.spoony.data.repositoryimpl.DummyRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.ExploreRepositoryImpl
import com.spoony.spoony.domain.repository.DummyRepository
import com.spoony.spoony.domain.repository.ExploreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindExploreRepository(exploreRepositoryImpl: ExploreRepositoryImpl): ExploreRepository
}
