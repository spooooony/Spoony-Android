package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class CheckNicknameDuplicationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        nickname: String,
        originalNickname: String?
    ): Result<Boolean> {
        if (nickname.isBlank()) {
            return Result.failure(IllegalArgumentException("닉네임이 비어있습니다"))
        }

        if (nickname == originalNickname) {
            return Result.success(false)
        }

        return userRepository.checkUserNameExist(nickname)
    }
}
