package com.spoony.spoony.domain.usecase

import com.spoony.spoony.domain.entity.RegionEntity
import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject

class GetRegionListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<RegionEntity>> {
        return userRepository.getRegionList()
    }
}
