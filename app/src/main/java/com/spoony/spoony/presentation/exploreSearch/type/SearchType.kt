package com.spoony.spoony.presentation.exploreSearch.type

enum class SearchType {
    USER,
    REVIEW
}

fun SearchType.toKoreanText(): String {
    return when (this) {
        SearchType.USER -> "유저"
        SearchType.REVIEW -> "리뷰"
    }
}
