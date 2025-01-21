package com.spoony.spoony.data.repositoryimpl

import android.net.Uri
import com.spoony.spoony.domain.repository.RegisterRepository
import com.spoony.spoony.presentation.register.model.Category
import com.spoony.spoony.presentation.register.model.Place
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor() : RegisterRepository {
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

    override suspend fun searchPlace(query: String, display: Int): Result<List<Place>> = Result.success(
        listOf(
            Place(
                placeName = "스타벅스 강남대로점",
                placeAddress = "서울특별시 서초구 서초동 1305-7",
                placeRoadAddress = "서울특별시 서초구 강남대로 369 (서초동)",
                latitude = 37.4979,
                longitude = 127.0276
            ),
            Place(
                placeName = "스타벅스 신촌역점",
                placeAddress = "서울특별시 서대문구 창천동 30-1",
                placeRoadAddress = "서울특별시 서대문구 연세로 10-1 (창천동)",
                latitude = 37.5598,
                longitude = 126.9423
            ),
            Place(
                placeName = "스타벅스 홍대역점",
                placeAddress = "서울특별시 마포구 서교동 358-11",
                placeRoadAddress = "서울특별시 마포구 양화로 166 (서교동)",
                latitude = 37.5569,
                longitude = 126.9237
            ),
            Place(
                placeName = "스타벅스 부산센텀시티점",
                placeAddress = "부산광역시 해운대구 우동 1505",
                placeRoadAddress = "부산광역시 해운대구 센텀남대로 35 (우동)",
                latitude = 35.1698,
                longitude = 129.1315
            ),
            Place(
                placeName = "스타벅스 대구동성로점",
                placeAddress = "대구광역시 중구 동성로3가 11",
                placeRoadAddress = "대구광역시 중구 동성로 55 (동성로3가)",
                latitude = 35.8703,
                longitude = 128.5978
            )
        )
    )

    override suspend fun checkDuplicatePlace(
        userId: Long,
        latitude: Double,
        longitude: Double
    ): Result<Boolean> = runCatching {
        latitude == 35.8703 && longitude == 128.5978
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
