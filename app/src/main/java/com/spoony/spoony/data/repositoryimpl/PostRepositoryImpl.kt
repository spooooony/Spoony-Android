package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PostEntity
import com.spoony.spoony.domain.repository.PostRepository
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf

class PostRepositoryImpl @Inject constructor() : PostRepository {
    override suspend fun getPost(postId: Int): Result<PostEntity> =
        Result.success(
            PostEntity(
                postId = postId,
                userId = 1,
                photoUrlList = persistentListOf(
                    "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/post/%2Fc4c71962-10df-4b79-999d-f17737bfa5a6starbucks_1.jpg",
                    "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/post/%2Fe14dc4d1-f11c-4187-a120-54ab0c2493b2starbucks_2.jpg",
                    "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/post/%2Fd25a3ca9-f107-47e2-8ca7-b00ed9a1704cstarbucks_3.jpg"
                ),
                title = "스타벅스 강남R점 후기",
                date = "2025년 1월",
                menuList = persistentListOf(
                    "아메리카노",
                    "카페라떼"
                ),
                description = "강남R점에서 커피를 마셨습니다. 분위기가 좋았어요!",
                placeName = "스타벅스 강남R점",
                placeAddress = "서울특별시 강남구 역삼동 825",
                latitude = 37.497711,
                longitude = 127.028439,
                addMapCount = 1,
                isAddMap = false,
                isScooped = false,
                category = CategoryEntity(
                    categoryId = 1,
                    categoryName = "카페",
                    iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/cafe_color.png",
                    unSelectedIconUrl = "unSelectedIconUrl",
                    textColor = "FF7E84",
                    backgroundColor = "FFE4E5"
                )
            )
        )

    override suspend fun postScoopPost(postId: Int, userId: Int): Result<Boolean> =
        Result.success(
            true
        )

    override suspend fun postAddMap(postId: Int, userId: Int): Result<Boolean> =
        Result.success(
            true
        )

    override suspend fun deletePinMap(postId: Int, userId: Int): Result<Boolean> =
        Result.success(
            true
        )
}
