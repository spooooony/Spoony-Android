package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.SpoonDrawListReponseDto
import com.spoony.spoony.domain.entity.SpoonListEntity

fun SpoonDrawListReponseDto.toDomain(): SpoonListEntity = SpoonListEntity(
    spoonResultList = this.spoonDrawList.map { it.toDomain() },
    totalSpoonCount = this.totalSpoonCount,
    weeklySpoonCount = this.weeklySpoonCount
)
