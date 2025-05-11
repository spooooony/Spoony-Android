package com.spoony.spoony.presentation.userpage.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.main100
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.presentation.userpage.model.ReviewData
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state: MutableStateFlow<UserPageState> = MutableStateFlow(
        UserPageState(
            userType = UserType.MY_PAGE,
            profile = UserProfile(),
            spoonCount = 0
        )
    )
    val state: StateFlow<UserPageState>
        get() = _state.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val profile = UserProfile(
                profileId = 4,
                imageUrl = "https://avatars.githubusercontent.com/u/160750136?v=4",
                nickname = "톳시",
                region = "에도 막부",
                introduction = "오타쿠의 패왕",
                reviewCount = 12,
                followerCount = 150,
                followingCount = 230
            )
            _state.update { it.copy(profile = profile) }
            getUserReviews()
        }
    }

    fun getSpoonCount() {
        viewModelScope.launch {
            authRepository.getSpoonCount()
                .onSuccess { count ->
                    _state.update { it.copy(spoonCount = count) }
                }
        }
    }

    private fun getUserReviews() {
        viewModelScope.launch {
            val mockReviews = persistentListOf(
                ReviewData(
                    reviewId = 1,
                    content = "크리스짱짱맨의 돈까스는 진짜 맛있어요! 소스가 일품이고 돈까스가 두껍고 육즙이 가득해요. 다음에 또 방문할 예정입니다.",
                    category = ReviewCardCategory(
                        text = "맛집",
                        iconUrl = "",
                        textColor = main400,
                        backgroundColor = main100
                    ),
                    username = "톳시",
                    userRegion = "에도 막부",
                    date = "2023.07.15",
                    addMapCount = 42,
                    imageList = persistentListOf(
                        "https://picsum.photos/id/102/200/200",
                        "https://picsum.photos/id/103/200/200",
                        "https://picsum.photos/id/104/200/200"
                    )
                ),
                ReviewData(
                    reviewId = 2,
                    content = "라멘은 정말 일본 현지의 맛을 그대로 재현했어요. 특히 돈코츠 라멘은 국물이 진하고 면발이 쫄깃해서 좋았습니다.",
                    category = ReviewCardCategory(
                        text = "라멘",
                        iconUrl = "",
                        textColor = main400,
                        backgroundColor = main100
                    ),
                    username = "톳시",
                    userRegion = "에도 막부",
                    date = "2023.06.30",
                    addMapCount = 23,
                    imageList = persistentListOf(
                        "https://picsum.photos/id/292/200/200"
                    )
                )
            )

            _state.update { it.copy(reviews = mockReviews) }
        }
    }
}
