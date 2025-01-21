package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.FeedEntity
import com.spoony.spoony.domain.repository.ExploreRepository
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor() : ExploreRepository {
    override suspend fun getCategoryList(): Result<List<CategoryEntity>> = Result.success(
        listOf(
            CategoryEntity(
                categoryId = 1,
                categoryName = "전체",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/all_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/all_black.png"
            ),
            CategoryEntity(
                categoryId = 2,
                categoryName = "로컬 수저",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/local_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/all_black.png"
            ),
            CategoryEntity(
                categoryId = 3,
                categoryName = "한식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 4,
                categoryName = "일식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/japanese_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/japanese_black.png"
            ),
            CategoryEntity(
                categoryId = 5,
                categoryName = "중식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/chinese_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/chinese_black.png"
            ),
            CategoryEntity(
                categoryId = 6,
                categoryName = "양식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/american_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/american_black.png"
            ),
            CategoryEntity(
                categoryId = 7,
                categoryName = "퓨전/세계요리",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/world_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/world_black.png"
            ),
            CategoryEntity(
                categoryId = 8,
                categoryName = "카페",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_black.png"
            ),
            CategoryEntity(
                categoryId = 9,
                categoryName = "주류",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/drink_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/drink_black.png"
            )
        )
    )

    override suspend fun getFeedList(
        userId: Int,
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>> = Result.success(
        listOf(
            FeedEntity(
                userId = 3,
                userName = "두더지",
                userRegion = "용산구",
                postId = 3,
                title = "스타벅스 삼성역섬유센터R점 리뷰",
                categoryInfo = CategoryEntity(
                    categoryId = 2,
                    categoryName = "카페",
                    iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_color.png",
                    textColor = "FFE4E5",
                    backgroundColor = "FF7E84"
                ),
                zzimCount = 1
            ),
            FeedEntity(
                userId = 3,
                userName = "두더지",
                userRegion = "용산구",
                postId = 4,
                title = "스타벅스 삼성역섬유센터R점 리뷰",
                categoryInfo = CategoryEntity(
                    categoryId = 2,
                    categoryName = "카페",
                    iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_color.png",
                    textColor = "FFE4E5",
                    backgroundColor = "FF7E84"
                ),
                zzimCount = 1
            ),
            FeedEntity(
                userId = 3,
                userName = "두더지",
                userRegion = "용산구",
                postId = 5,
                title = "스타벅스 삼성역섬유센터R점 리뷰",
                categoryInfo = CategoryEntity(
                    categoryId = 2,
                    categoryName = "카페",
                    iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_color.png",
                    textColor = "FFE4E5",
                    backgroundColor = "FF7E84"
                ),
                zzimCount = 1
            ),
            FeedEntity(
                userId = 3,
                userName = "두더지",
                userRegion = "용산구",
                postId = 6,
                title = "스타벅스 삼성역섬유센터R점 리뷰",
                categoryInfo = CategoryEntity(
                    categoryId = 2,
                    categoryName = "카페",
                    iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_color.png",
                    textColor = "FFE4E5",
                    backgroundColor = "FF7E84"
                ),
                zzimCount = 1
            ),
            FeedEntity(
                userId = 3,
                userName = "두더지",
                userRegion = "용산구",
                postId = 7,
                title = "스타벅스 삼성역섬유센터R점 리뷰",
                categoryInfo = CategoryEntity(
                    categoryId = 2,
                    categoryName = "카페",
                    iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_color.png",
                    textColor = "FFE4E5",
                    backgroundColor = "FF7E84"
                ),
                zzimCount = 1
            )
        )
    )
}
