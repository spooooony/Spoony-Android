package com.spoony.spoony.data.di

import com.spoony.spoony.data.repositoryimpl.AuthRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.CategoryRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.ExploreRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.MapRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.PostRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.RegionRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.RegisterRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.ReportRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.ReviewRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.SpoonRepositoryImpl
import com.spoony.spoony.data.repositoryimpl.UserRepositoryImpl
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.CategoryRepository
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.RegionRepository
import com.spoony.spoony.domain.repository.RegisterRepository
import com.spoony.spoony.domain.repository.ReportRepository
import com.spoony.spoony.domain.repository.ReviewRepository
import com.spoony.spoony.domain.repository.SpoonRepository
import com.spoony.spoony.domain.repository.UserRepository
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
    abstract fun bindMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

    @Binds
    @Singleton
    abstract fun bindExploreRepository(exploreRepositoryImpl: ExploreRepositoryImpl): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindSpoonRepository(spoonRepositoryImpl: SpoonRepositoryImpl): SpoonRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindRegionRepository(regionRepositoryImpl: RegionRepositoryImpl): RegionRepository
}
