package com.spoony.spoony.presentation.explore.model

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.RegionEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ExploreFilter(
    val id: Int,
    val type: String,
    val name: String,
    val selectedIconUrl: String = "",
    val unSelectedIconUrl: String = ""
)

fun CategoryEntity.toExploreFilter(): ExploreFilter {
    return ExploreFilter(
        id = this.categoryId,
        name = this.categoryName,
        type = "category",
        selectedIconUrl = this.iconUrl,
        unSelectedIconUrl = this.unSelectedIconUrl ?: ""
    )
}

fun RegionEntity.toExploreFilter(): ExploreFilter {
    return ExploreFilter(
        id = this.regionId,
        name = this.regionName,
        type = "region"
    )
}

object ExploreFilterDataProvider {
    fun getDefaultPropertyFilter() = persistentListOf(
        ExploreFilter(
            id = 1,
            name = "로컬 리뷰",
            type = "property"
        )
    )
    fun getDefaultCategoryFilter(): ImmutableList<ExploreFilter> {
        val categoryData = persistentListOf(
            Triple(3, "한식", ""),
            Triple(4, "일식", ""),
            Triple(5, "중식", ""),
            Triple(6, "양식", ""),
            Triple(7, "카페", ""),
            Triple(8, "주류", ""),
            Triple(9, "퓨전/세계요리", "")
        )

        return categoryData.map { (categoryId, categoryName, _) ->
            ExploreFilter(
                id = categoryId,
                name = categoryName,
                type = "category",
                selectedIconUrl = "",
                unSelectedIconUrl = ""
            )
        }.toImmutableList()
    }
    fun getDefaultRegionFilter(): ImmutableList<ExploreFilter> {
        val regionData = persistentListOf(
            Pair(1, "강남구"),
            Pair(2, "강동구"),
            Pair(3, "강북구"),
            Pair(4, "강서구"),
            Pair(5, "관악구"),
            Pair(6, "광진구"),
            Pair(7, "구로구"),
            Pair(8, "금천구"),
            Pair(9, "노원구"),
            Pair(10, "도봉구"),
            Pair(11, "동대문구"),
            Pair(12, "동작구"),
            Pair(13, "마포구"),
            Pair(14, "서대문구"),
            Pair(15, "서초구"),
            Pair(16, "성동구"),
            Pair(17, "성북구"),
            Pair(18, "송파구"),
            Pair(19, "양천구"),
            Pair(20, "영등포구"),
            Pair(21, "용산구"),
            Pair(22, "은평구"),
            Pair(23, "종로구"),
            Pair(24, "중구"),
            Pair(25, "중랑구")
        )

        return regionData.map { (id, name) ->
            ExploreFilter(
                id = id,
                name = name,
                type = "region"
            )
        }.toImmutableList()
    }
    fun getDefaultAgeFilter(): ImmutableList<ExploreFilter> {
        val ageData = persistentListOf(
            Pair(10, "10대"),
            Pair(20, "20대"),
            Pair(30, "30대"),
            Pair(40, "40대+")
        )

        return ageData.map { (id, name) ->
            ExploreFilter(
                id = id,
                name = name,
                type = "age"
            )
        }.toImmutableList()
    }
}
