package com.spoony.spoony.core.network.di

import com.spoony.spoony.core.network.AuthenticatorErrorHandler
import com.spoony.spoony.core.network.AuthenticatorErrorHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorHandlerModule {
    @Binds
    @Singleton
    abstract fun provideAuthenticatorErrorHandler(authenticatorErrorHandlerImpl: AuthenticatorErrorHandlerImpl): AuthenticatorErrorHandler
}
