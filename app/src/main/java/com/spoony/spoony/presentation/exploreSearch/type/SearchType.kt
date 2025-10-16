package com.spoony.spoony.presentation.exploreSearch.type

enum class SearchType(
    val trackingCode: String
) {
    USER("user"),
    REVIEW("review")
}

fun SearchType.toKoreanText(): String {
    return when (this) {
        SearchType.USER -> "유저"
        SearchType.REVIEW -> "리뷰"
    }
}
