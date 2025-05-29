package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.BlockingListResponseDto
import com.spoony.spoony.data.dto.response.FollowListResponseDto
import com.spoony.spoony.domain.entity.BlockingListEntity
import com.spoony.spoony.data.dto.response.User as UserDto
import com.spoony.spoony.domain.entity.FollowListEntity
import com.spoony.spoony.domain.entity.User as UserEntity

fun BlockingListResponseDto.toDomain() = BlockingListEntity(
    users = this.users.map { it.toDomain() }
)
