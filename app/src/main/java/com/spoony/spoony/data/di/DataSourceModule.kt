package com.spoony.spoony.data.di

import com.spoony.spoony.data.datasource.AuthRemoteDataSource
import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.datasource.MapRemoteDataSource
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.datasource.RegionRemoteDataSource
import com.spoony.spoony.data.datasource.ReportDataSource
import com.spoony.spoony.data.datasource.ReviewRemoteDataSource
import com.spoony.spoony.data.datasource.SpoonDataSource
import com.spoony.spoony.data.datasource.UserRemoteDataSource
import com.spoony.spoony.data.datasourceimpl.AuthRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.CategoryDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.ExploreRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.MapRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.PlaceDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.PostRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.RegionRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.ReportDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.ReviewRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.SpoonDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindPostDataSource(postRemoteDataSourceImpl: PostRemoteDataSourceImpl): PostRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPlaceDataSource(
        placeDataSourceImpl: PlaceDataSourceImpl
    ): PlaceDataSource

    @Binds
    @Singleton
    abstract fun bindCategoryDataSource(
        categoryDataSourceImpl: CategoryDataSourceImpl
    ): CategoryDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindReportDataSource(
        reportDataSourceImpl: ReportDataSourceImpl
    ): ReportDataSource

    @Binds
    @Singleton
    abstract fun bindExploreDataSource(
        exploreRemoteDataSourceImpl: ExploreRemoteDataSourceImpl
    ): ExploreRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMapDataSource(
        mapRemoteDataSourceImpl: MapRemoteDataSourceImpl
    ): MapRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRegionDataSource(
        regionRemoteDataSourceImpl: RegionRemoteDataSourceImpl
    ): RegionRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSpoonDataSource(
        spoonDataSourceImpl: SpoonDataSourceImpl
    ): SpoonDataSource

    @Binds
    @Singleton
    abstract fun bindReviewSource(
        remoteDataSourceImpl: ReviewRemoteDataSourceImpl
    ): ReviewRemoteDataSource
}
