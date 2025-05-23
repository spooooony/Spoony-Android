package com.spoony.spoony.data.di

import com.spoony.spoony.core.network.qualifier.Auth
import com.spoony.spoony.data.service.AuthService
import com.spoony.spoony.data.service.CategoryService
import com.spoony.spoony.data.service.ExploreService
import com.spoony.spoony.data.service.MapService
import com.spoony.spoony.data.service.PlaceService
import com.spoony.spoony.data.service.PostService
import com.spoony.spoony.data.service.ReportService
import com.spoony.spoony.data.service.SpoonService
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
    fun providePostService(@Auth retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)

    @Provides
    @Singleton
    fun providePlaceService(@Auth retrofit: Retrofit): PlaceService =
        retrofit.create(PlaceService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(@Auth retrofit: Retrofit): CategoryService =
        retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(@Auth retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideReportService(@Auth retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)

    @Provides
    @Singleton
    fun provideExploreService(@Auth retrofit: Retrofit): ExploreService =
        retrofit.create(ExploreService::class.java)

    @Provides
    @Singleton
    fun provideMapService(@Auth retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@Auth retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideSpoonService(@Auth retrofit: Retrofit): SpoonService =
        retrofit.create(SpoonService::class.java)
}
