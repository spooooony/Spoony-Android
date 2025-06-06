package com.spoony.spoony.presentation.explore.type

enum class SortingOption(
    val stringValue: String,
    val stringCode: String
) {
    LATEST("최신순", "createdAt"),
    POPULARITY("저장 많은 순", "zzimCount")
}