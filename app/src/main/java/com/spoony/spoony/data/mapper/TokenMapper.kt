package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.TokenResponseDto
import com.spoony.spoony.domain.entity.TokenEntity

fun TokenResponseDto.toDomain(): TokenEntity = TokenEntity(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)
