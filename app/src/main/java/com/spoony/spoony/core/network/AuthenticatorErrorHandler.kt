package com.spoony.spoony.core.network

interface AuthenticatorErrorHandler {
    fun handleTokenNullError()
    suspend fun handleTokenReissueError()
}
