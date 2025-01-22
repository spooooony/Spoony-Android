package com.spoony.spoony.data.repositoryimpl

import android.net.Uri
import com.spoony.spoony.data.datasource.PlaceDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.data.mapper.toPresentation
import com.spoony.spoony.domain.repository.RegisterRepository
import com.spoony.spoony.presentation.register.model.Category
import com.spoony.spoony.presentation.register.model.Place
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val placeDataSource: PlaceDataSource
) : RegisterRepository {
    override suspend fun getCategories(): Result<List<Category>> = Result.success(
        listOf(
            Category(
                categoryId = 2,
                categoryName = "한식",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            Category(
                categoryId = 3,
                categoryName = "일식",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            Category(
                categoryId = 4,
                categoryName = "중식",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            Category(
                categoryId = 5,
                categoryName = "양식",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            Category(
                categoryId = 6,
                categoryName = "퓨전/세계요리",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            Category(
                categoryId = 7,
                categoryName = "카페",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            ),
            Category(
                categoryId = 8,
                categoryName = "주류",
                iconUrlSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_white.png",
                iconUrlNotSelected = "https://spoony-storage.s3.ap-northeast-2.amazonaws.com/category/icons/korean_black.png"
            )
        )
    )

    override suspend fun searchPlace(query: String, display: Int): Result<List<Place>> = runCatching {
        placeDataSource.getPlaces(query, display).data!!.placeList
            .map { it.toDomain().toPresentation() }
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
