package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.entity.TokenEntity
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(
        token: String,
        platform: String
    ): Result<TokenEntity?> {
        return authRepository.signIn(
            token = token,
            platform = platform
        ).mapCatching { tokenEntity ->
            if (tokenEntity != null) {
                tokenRepository.updateTokens(tokenEntity)
                return Result.success(tokenEntity)
            }

            tokenRepository.updateCachedAccessToken(token)
            return Result.success(null)
        }
    }
}
