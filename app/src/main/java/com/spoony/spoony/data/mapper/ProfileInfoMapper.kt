package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.ProfileInfoResponseDto
import com.spoony.spoony.domain.entity.ProfileInfoEntity

fun ProfileInfoResponseDto.toDomain() = ProfileInfoEntity(
    userName = this.userName,
    regionName = this.regionName ?: "",
    introduction = this.introduction ?: "",
    birth = this.birth ?: "",
    imageLevel = this.imageLevel
)
