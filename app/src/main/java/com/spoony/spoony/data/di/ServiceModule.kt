package com.spoony.spoony.data.di

import com.spoony.spoony.data.service.AuthService
import com.spoony.spoony.data.service.CategoryService
import com.spoony.spoony.data.service.ExploreService
import com.spoony.spoony.data.service.MapService
import com.spoony.spoony.data.service.PlaceService
import com.spoony.spoony.data.service.PostService
import com.spoony.spoony.data.service.RegionService
import com.spoony.spoony.data.service.ReportService
import com.spoony.spoony.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)

    @Provides
    @Singleton
    fun providePlaceService(retrofit: Retrofit): PlaceService =
        retrofit.create(PlaceService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService =
        retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideReportService(retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)

    @Provides
    @Singleton
    fun provideExploreService(retrofit: Retrofit): ExploreService =
        retrofit.create(ExploreService::class.java)

    @Provides
    @Singleton
    fun provideMapService(retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideRegionService(retrofit: Retrofit): RegionService =
        retrofit.create(RegionService::class.java)
}
