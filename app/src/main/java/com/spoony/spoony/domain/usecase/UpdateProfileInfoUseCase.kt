package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.entity.ProfileUpdateEntity
import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(profileUpdateEntity: ProfileUpdateEntity): Result<Unit> {
        return userRepository.updateMyProfileInfo(profileUpdateEntity)
    }
}
