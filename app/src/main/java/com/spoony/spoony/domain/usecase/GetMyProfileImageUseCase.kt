package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.entity.ProfileImageEntity
import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class GetMyProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<ProfileImageEntity> {
        return userRepository.getMyProfileImage()
    }
}
