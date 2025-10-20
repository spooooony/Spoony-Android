package com.spoony.spoony.presentation.explore.type

enum class SortingOption(
    val stringValue: String,
    val stringCode: String,
    val trackingCode: String
) {
    LATEST("최신순", "createdAt", "latest"),
    POPULARITY("저장 많은 순", "zzimCount", "most_saved")
}
