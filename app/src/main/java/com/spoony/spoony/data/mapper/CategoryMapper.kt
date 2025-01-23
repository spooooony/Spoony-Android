package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.CategoryMonoDto
import com.spoony.spoony.domain.entity.CategoryEntity

fun CategoryMonoDto.toDomain(): CategoryEntity =
    CategoryEntity(
        categoryId = categoryId,
        categoryName = categoryName,
        iconUrl = iconUrlSelected,
        unSelectedIconUrl = iconUrlNotSelected
    )
