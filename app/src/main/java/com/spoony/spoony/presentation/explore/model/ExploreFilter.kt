package com.spoony.spoony.presentation.explore.model

data class ExploreFilter(
    val id: Int,
    val type: String,
    val name: String,
    val selectedIconUrl: String = "",
    val unSelectedIconUrl: String = ""
)

object ExploreFilterDataProvider {
    fun getDefaultPropertyFilter() = listOf(
        ExploreFilter(
            id = 1,
            name = "로컬 리뷰",
            type = "property"
        )
    )
    fun getDefaultCategoryFilter(): List<ExploreFilter> {
        val categoryData = listOf(
            Triple(2, "한식", "korean"),
            Triple(3, "일식", "japanese"),
            Triple(4, "중식", "chinese"),
            Triple(5, "양식", "american"),
            Triple(6, "카페", "cafe"),
            Triple(7, "주류", "drink"),
            Triple(8, "퓨전/세계요리", "world")
        )

        return categoryData.map { (id, name, icon) ->
            ExploreFilter(
                id = id,
                name = name,
                type = "category",
                selectedIconUrl = "",
                unSelectedIconUrl = ""
            )
        }
    }
    fun getDefaultRegionFilter(): List<ExploreFilter> {
        val regionData = listOf(
            Pair(9, "강남구"),
            Pair(10, "강동구"),
            Pair(11, "강북구"),
            Pair(12, "강서구"),
            Pair(13, "관악구"),
            Pair(14, "광진구"),
            Pair(15, "구로구"),
            Pair(16, "금천구"),
            Pair(17, "노원구"),
            Pair(18, "도봉구"),
            Pair(19, "동대문구"),
            Pair(20, "동작구"),
            Pair(21, "마포구"),
            Pair(22, "서대문구"),
            Pair(23, "서초구"),
            Pair(24, "성동구"),
            Pair(25, "성북구"),
            Pair(26, "송파구"),
            Pair(27, "양천구"),
            Pair(28, "영등포구"),
            Pair(29, "용산구"),
            Pair(30, "은평구"),
            Pair(31, "종로구"),
            Pair(32, "중구"),
            Pair(33, "중랑구")
        )

        return regionData.map { (id, name) ->
            ExploreFilter(
                id = id,
                name = name,
                type = "region"
            )
        }
    }
    fun getDefaultAgeFilter(): List<ExploreFilter> {
        val ageData = listOf(
            Pair(34, "10대"),
            Pair(35, "20대"),
            Pair(36, "30대"),
            Pair(37, "40대+")
        )

        return ageData.map { (id, name) ->
            ExploreFilter(
                id = id,
                name = name,
                type = "age"
            )
        }
    }
}
