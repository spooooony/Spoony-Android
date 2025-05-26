package com.spoony.spoony.data.repositoryimpl

import com.spoony.spoony.data.datasource.ExploreRemoteDataSource
import com.spoony.spoony.data.mapper.toDomain
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.FeedEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import com.spoony.spoony.domain.entity.UserEntity
import com.spoony.spoony.domain.repository.ExploreRepository
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf

class ExploreRepositoryImpl @Inject constructor(
    private val exploreRemoteDataSource: ExploreRemoteDataSource
) : ExploreRepository {
    override suspend fun getFeedList(
        categoryId: Int,
        locationQuery: String,
        sortBy: String
    ): Result<List<FeedEntity>> = runCatching {
        exploreRemoteDataSource.getFeedList(
            categoryId = categoryId,
            query = locationQuery,
            sortBy = sortBy
        ).data!!.feedsResponseList.map {
            it.toDomain()
        }
    }

    override suspend fun getAllFeedList(): Result<List<PlaceReviewEntity>> = Result.success(
        listOf(
            PlaceReviewEntity(
                reviewId = 1,
                userId = 101,
                userName = "해피푸딩",
                userRegion = "서대문구",
                description = "오늘은 특별한 날이라 친구들과 함께 이곳에 왔어요. 분위기가 정말 좋았고 직원분들도 친절했어요. 다음에 또 방문하고 싶네요! 가격대비 만족스러웠습니다.",
                photoUrlList = persistentListOf(), // 이미지 없음
                menuList = persistentListOf("아메리카노", "치즈케이크", "크로플"),
                placeName = "카페 세렌디피티",
                placeAddress = "서울 서대문구 연희로 123",
                latitude = 37.5665,
                longitude = 126.9780,
                category = CategoryEntity(
                    categoryId = 3,
                    categoryName = "양식",
                    backgroundColor = "FFCEC6",
                    textColor = "FF5235",
                    iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c"
                ),
                addMapCount = 42,
                isMine = false,
                isAddMap = true,
                isScooped = false,
                createdAt = "2024-05-10T13:45:30Z"
            ),

            PlaceReviewEntity(
                reviewId = 2,
                userId = 102,
                userName = "먹방왕자",
                userRegion = "강남구",
                description = "이 곳의 스테이크는 정말 최고예요! 미디엄 레어로 주문했는데 정말 딱 알맞게 나왔어요. 특히 트러플 소스를 곁들인 감자 퓨레가 환상적이었습니다. 특별한 날에 방문하기 좋은 곳입니다.",
                photoUrlList = persistentListOf(
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4"
                ),
                menuList = persistentListOf("티본 스테이크", "트러플 감자 퓨레", "레드와인"),
                placeName = "스테이크 하우스",
                placeAddress = "서울 강남구 테헤란로 456",
                latitude = 37.5013,
                longitude = 127.0396,
                category = CategoryEntity(
                    categoryId = 3,
                    categoryName = "양식",
                    backgroundColor = "FFCEC6",
                    textColor = "FF5235",
                    iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c"
                ),
                addMapCount = 78,
                isMine = true,
                isAddMap = true,
                isScooped = true,
                createdAt = "2024-05-12T19:22:15Z"
            ),

            PlaceReviewEntity(
                reviewId = 3,
                userId = 103,
                userName = "빵순이",
                userRegion = "성동구",
                description = "이곳의 크루아상은 정말 환상적이에요! 겉은 바삭하고 속은 부드러워요. 특히 초코 크루아상이 제일 맛있었어요. 아침에 갓 구운 빵을 먹을 수 있어서 좋았습니다. 커피와 함께 먹으니 더 맛있었어요.",
                photoUrlList = persistentListOf(
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4"
                ),
                menuList = persistentListOf("초코 크루아상", "플레인 크루아상", "카페라떼"),
                placeName = "오 봉 빵",
                placeAddress = "서울 성동구 왕십리로 789",
                latitude = 37.5615,
                longitude = 127.0380,
                category = CategoryEntity(
                    categoryId = 3,
                    categoryName = "양식",
                    backgroundColor = "FFCEC6",
                    textColor = "FF5235",
                    iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c"
                ),
                addMapCount = 56,
                isMine = false,
                isAddMap = false,
                isScooped = true,
                createdAt = "2024-05-11T08:15:45Z"
            ),

            PlaceReviewEntity(
                reviewId = 4,
                userId = 104,
                userName = "술꾼이",
                userRegion = "마포구",
                description = "이자카야인데 친구랑 가서 안주만 5개 넘게 시킴.. 명성이 자자한 고등어봉 초밥은 꼭 시키세요! 입에 넣자마자 사르르 녹아요. 사케도 종류가 다양해서 좋았어요. 분위기도 좋고 조용히 대화하기 좋아요.",
                photoUrlList = persistentListOf(
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4"
                ),
                menuList = persistentListOf("고등어봉 초밥", "모듬 사시미", "준마이 사케"),
                placeName = "이자카야 토모",
                placeAddress = "서울 마포구 홍대로 101",
                latitude = 37.5558,
                longitude = 126.9234,
                category = CategoryEntity(
                    categoryId = 3,
                    categoryName = "양식",
                    backgroundColor = "FFCEC6",
                    textColor = "FF5235",
                    iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c"
                ),
                addMapCount = 120,
                isMine = false,
                isAddMap = true,
                isScooped = true,
                createdAt = "2024-05-09T21:30:00Z"
            ),

            PlaceReviewEntity(
                reviewId = 5,
                userId = 105,
                userName = "문화인",
                userRegion = "종로구",
                description = "이번 전시회는 정말 눈이 즐거운 시간이었어요. 특히 2층의 미디어 아트 작품이 인상적이었습니다. 입장료도 합리적이고 전시 해설도 좋았어요. 주말에는 사람이 많으니 평일 방문을 추천합니다.",
                photoUrlList = persistentListOf(
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                    "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4"
                ),
                menuList = persistentListOf("입장권", "전시 도록", "기념품"),
                placeName = "서울 현대 미술관",
                placeAddress = "서울 종로구 사간동 45",
                latitude = 37.5796,
                longitude = 126.9810,
                category = CategoryEntity(
                    categoryId = 3,
                    categoryName = "양식",
                    backgroundColor = "FFCEC6",
                    textColor = "FF5235",
                    iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c"
                ),
                addMapCount = 89,
                isMine = true,
                isAddMap = false,
                isScooped = false,
                createdAt = "2024-05-08T14:20:10Z"
            )
        )
    )

    override suspend fun getPlaceReviewByKeyword(query: String): Result<List<PlaceReviewEntity>> = runCatching {
        exploreRemoteDataSource.getPlaceReviewByKeyword(
            query = query
        ).data!!.postSearchResultList.map { it.toDomain() }
    }

    override suspend fun getUserListByKeyword(query: String): Result<List<UserEntity>> = runCatching {
        exploreRemoteDataSource.getUserListByKeyword(
            query = query
        ).data!!.userSimpleResponseDTO.map { it.toDomain() }
    }
}
