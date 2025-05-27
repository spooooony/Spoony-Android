package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.request.ProfileUpdateRequestDto
import com.spoony.spoony.domain.entity.ProfileUpdateEntity

fun ProfileUpdateEntity.toDto() = ProfileUpdateRequestDto(
    userName = this.userName,
    regionId = this.regionId,
    introduction = this.introduction,
    birth = this.birth,
    imageLevel = this.imageLevel
)
