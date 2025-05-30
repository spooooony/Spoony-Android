package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.BlockingListResponseDto
import com.spoony.spoony.domain.entity.BlockingListEntity

fun BlockingListResponseDto.toDomain() = BlockingListEntity(
    users = this.users.map { it.toDomain() }
)
