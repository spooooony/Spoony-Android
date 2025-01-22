package com.spoony.spoony.data.repositoryimpl

import android.net.Uri
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceEntity
import com.spoony.spoony.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val placeDataSource: PlaceDataSource
) : RegisterRepository {
    override suspend fun getCategories(): Result<List<CategoryEntity>> = Result.success(
        listOf(
            CategoryEntity(
                categoryId = 2,
                categoryName = "한식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 3,
                categoryName = "일식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 4,
                categoryName = "중식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 5,
                categoryName = "양식",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 6,
                categoryName = "퓨전/세계요리",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 7,
                categoryName = "카페",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            CategoryEntity(
                categoryId = 8,
                categoryName = "주류",
                iconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                unSelectedIconUrl = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            )
        )
    )

    override suspend fun searchPlace(query: String, display: Int): Result<List<PlaceEntity>> = runCatching {
        placeDataSource.getPlaces(query, display).data!!.placeList
            .map { it.toDomain() }
    }

    override suspend fun checkDuplicatePlace(
        userId: Long,
        latitude: Double,
        longitude: Double
    ): Result<Boolean> = runCatching {
        placeDataSource.checkDuplicatePlace(userId, latitude, longitude).data!!.duplicate
    }

    override suspend fun registerPost(
        userId: Long,
        title: String,
        description: String,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        latitude: Double,
        longitude: Double,
        categoryId: Int,
        menuList: List<String>,
        photos: List<Uri>
    ): Result<Unit> = runCatching {
        // TODO: Multipart API 호출 구현
    }
}
