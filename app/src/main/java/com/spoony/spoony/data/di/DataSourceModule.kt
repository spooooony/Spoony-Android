package com.spoony.spoony.data.di

import com.spoony.spoony.data.datasource.CategoryDataSource
import com.spoony.spoony.data.datasource.DummyRemoteDataSource
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.datasource.PostRemoteDataSource
import com.spoony.spoony.data.datasource.ReportDataSource
import com.spoony.spoony.data.datasourceimpl.CategoryDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.DummyRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.PlaceDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.PostRemoteDataSourceImpl
import com.spoony.spoony.data.datasourceimpl.ReportDataSourceImpl
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
    abstract fun bindDummyDataSource(dummyRemoteDataSourceImpl: DummyRemoteDataSourceImpl): DummyRemoteDataSource

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
    abstract fun bindReportDataSource(
        reportDataSourceImpl: ReportDataSourceImpl
    ): ReportDataSource
}
