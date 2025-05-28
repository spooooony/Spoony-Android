package com.spoony.spoony.domain.usecase

import com.spoony.spoony.core.designsystem.model.RegionModel
import javax.inject.Inject

class MatchRegionIdUseCase @Inject constructor() {
    operator fun invoke(
        regionName: String,
        regionList: List<RegionModel>
    ): Int? {
        if (regionName.isBlank()) return null

        val districtName = regionName.replace("서울 ", "")
        return regionList.find { it.regionName == districtName }?.regionId
    }
}
