package com.spoony.spoony.data.di

import com.spoony.spoony.data.repositoryimpl.DummyRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.ExploreRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.MapRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.PostRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.RegisterRepositoryImpl
import com.spoony.spoony.domain.repository.DummyRepository
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.RegisterRepository
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
    abstract fun bindMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

    @Binds
    @Singleton
    abstract fun bindExploreRepository(exploreRepositoryImpl: ExploreRepositoryImpl): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository
}
