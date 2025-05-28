package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.entity.ProfileInfoEntity
import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class GetMyProfileInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<ProfileInfoEntity> {
        return userRepository.getMyProfileInfo()
    }
}
