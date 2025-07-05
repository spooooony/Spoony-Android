package com.spoony.spoony.core.network

interface AuthenticatorErrorHandler {
    suspend fun handleTokenNullError()
    suspend fun handleTokenReissueError()
}
