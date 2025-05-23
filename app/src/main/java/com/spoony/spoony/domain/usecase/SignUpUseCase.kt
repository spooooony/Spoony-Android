package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.TokenRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(
        platform: String,
        userName: String,
        birth: String?,
        regionId: Int?,
        introduction: String?
    ): Result<Unit> {
        val token = tokenRepository.getCachedAccessToken()

        return authRepository.signUp(
            token = token,
            platform = platform,
            userName = userName,
            birth = birth,
            regionId = regionId,
            introduction = introduction
        ).fold(
            onSuccess = { tokenEntity ->
                try {
                    tokenRepository.updateCachedAccessToken(tokenEntity.accessToken)
                    tokenRepository.updateAccessToken(tokenEntity.accessToken)
                    tokenRepository.updateRefreshToken(tokenEntity.refreshToken)
                } catch (e: Throwable) {
                    return Result.failure(e)
                }

                return Result.success(Unit)
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}
