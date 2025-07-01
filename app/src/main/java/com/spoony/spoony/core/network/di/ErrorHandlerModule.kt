package com.spoony.spoony.core.network.di

import android.content.Context
import com.spoony.spoony.core.network.AuthenticatorErrorHandler
import com.spoony.spoony.core.network.AuthenticatorErrorHandlerImpl
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorHandlerModule {
    @Provides
    @Singleton
    fun provideAuthenticatorErrorHandler(
        @ApplicationContext context: Context,
        tokenRepository: TokenRepository
    ): AuthenticatorErrorHandler = AuthenticatorErrorHandlerImpl(context, tokenRepository)
}
