package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.SpoonDrawResponseDto
import com.spoony.spoony.domain.entity.SpoonEntity

fun SpoonDrawResponseDto.toDomain(): SpoonEntity = SpoonEntity(
    drawId = this.drawId,
    spoonType = SpoonEntity.SpoonType(
        spoonTypeId = this.spoonType.spoonTypeId,
        spoonName = this.spoonType.spoonName,
        spoonAmount = this.spoonType.spoonAmount,
        probability = this.spoonType.probability,
        spoonImage = this.spoonType.spoonImage
    ),
    localDate = this.localDate,
    weekStartDate = this.weekStartDate,
    createdAt = this.createdAt
)
