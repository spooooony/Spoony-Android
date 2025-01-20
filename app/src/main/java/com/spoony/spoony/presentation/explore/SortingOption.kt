package com.spoony.spoony.presentation.explore

enum class SortingOption(
    val stringValue: String,
    val stringCode: String
) {
    LATEST("최신순", "latest"),
    POPULARITY("인기순", "popularity")
}
