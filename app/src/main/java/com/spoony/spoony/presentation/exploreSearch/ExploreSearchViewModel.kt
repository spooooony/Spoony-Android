package com.spoony.spoony.presentation.exploreSearch

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ExploreSearchViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<ExploreSearchState> = MutableStateFlow(ExploreSearchState())
    val state: StateFlow<ExploreSearchState>
        get() = _state.asStateFlow()

    private val userInfoDummyList = persistentListOf(
        UserInfo(
            userId = 1,
            imageUrl = "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
            nickname = "맛있는여행자",
            region = "강남구"
        ),
        UserInfo(
            userId = 2,
            imageUrl = "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
            nickname = "먹방킹",
            region = "마포구"
        ),
        UserInfo(
            userId = 3,
            imageUrl = "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
            nickname = "푸드헌터",
            region = "해운대구"
        )
    )

    private val placeReviewInfoDummyList = persistentListOf(
        PlaceReviewInfo(
            reviewId = 1,
            userId = 1,
            userName = "맛있는여행자",
            userRegion = "서울 강남구",
            description = "이 식당의 스테이크는 정말 맛있어요! 특히 소스가 일품이었고 직원분들도 친절했습니다. 분위기도 고급스러워 특별한 날에 방문하기 좋을 것 같아요.",
            isMine = true,
            photoUrlList = persistentListOf(),
            category = ReviewCardCategory(
                text = "양식",
                iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c",
                backgroundColor = Color.hexToColor("FFCEC6"),
                textColor = Color.hexToColor("FF5235")
            ),
            addMapCount = 24,
            date = "약 5시간 전"
        ),
        PlaceReviewInfo(
            reviewId = 2,
            userId = 2,
            userName = "카페투어",
            userRegion = "경기 성남시",
            description = "조용하고 아늑한 분위기의 카페예요. 라떼 아트가 예술이고 케이크도 너무 맛있었습니다. 창가 자리에서 보는 전망이 특히 좋았어요.",
            isMine = false,
            photoUrlList = persistentListOf(
                "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4",
                "https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4"
            ),
            category = ReviewCardCategory(
                text = "양식",
                iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c",
                backgroundColor = Color.hexToColor("FFCEC6"),
                textColor = Color.hexToColor("FF5235")
            ),
            addMapCount = 42,
            date = "약 5시간 전"
        ),
        PlaceReviewInfo(
            reviewId = 3,
            userId = 3,
            userName = "푸드헌터",
            userRegion = "부산 해운대구",
            description = "해운대 바로 앞에 위치한 이 해산물 전문점은 신선함이 돋보입니다. 특히 회와 조개구이가 일품이었어요. 가격은 조금 있지만 그만한 가치가 있습니다!",
            isMine = false,
            photoUrlList = persistentListOf(),
            category = ReviewCardCategory(
                text = "양식",
                iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c",
                backgroundColor = Color.hexToColor("FFCEC6"),
                textColor = Color.hexToColor("FF5235")
            ),
            addMapCount = 56,
            date = "약 5시간 전"
        )
    )

    fun switchSearchType(
        searchType: SearchType
    ) {
        _state.update {
            it.copy(
                searchType = searchType
            )
        }
        search(_state.value.searchKeyword)
    }

    fun clearSearchKeyword() {
        _state.update {
            it.copy(
                searchKeyword = ""
            )
        }
    }

    fun search(keyword: String) {
        val keywordTrim = keyword.trim()
        if (keyword.isBlank()) return
        when (_state.value.searchType) {
            SearchType.USER -> {
                val updatedList = (listOf(keywordTrim) + _state.value.recentUserSearchQueryList.filterNot { it == keyword })
                    .take(6)
                    .toPersistentList()
                _state.update {
                    it.copy(
                        searchKeyword = keywordTrim,
                        recentUserSearchQueryList = updatedList,
                        userInfoList = UiState.Success(userInfoDummyList)
                    )
                }
            }
            SearchType.REVIEW -> {
                val updatedList = (listOf(keywordTrim) + _state.value.recentReviewSearchQueryList.filterNot { it == keyword })
                    .take(6)
                    .toPersistentList()
                _state.update {
                    it.copy(
                        searchKeyword = keywordTrim,
                        recentReviewSearchQueryList = updatedList,
                        placeReviewInfoList = UiState.Success(placeReviewInfoDummyList)
                    )
                }
            }
        }
    }

    fun removeRecentSearchItem(keyword: String) {
        when (_state.value.searchType) {
            SearchType.USER -> {
                val updatedList = _state.value.recentUserSearchQueryList.filterNot { it == keyword }.toPersistentList()
                _state.update {
                    it.copy(
                        recentUserSearchQueryList = updatedList
                    )
                }
            }
            SearchType.REVIEW -> {
                val updatedList = _state.value.recentReviewSearchQueryList.filterNot { it == keyword }.toPersistentList()
                _state.update {
                    it.copy(
                        recentReviewSearchQueryList = updatedList
                    )
                }
            }
        }
    }

    fun clearRecentSearchItem() {
        when (_state.value.searchType) {
            SearchType.USER -> {
                _state.update {
                    it.copy(
                        recentUserSearchQueryList = persistentListOf()
                    )
                }
            }
            SearchType.REVIEW -> {
                _state.update {
                    it.copy(
                        recentReviewSearchQueryList = persistentListOf()
                    )
                }
            }
        }
    }
}
