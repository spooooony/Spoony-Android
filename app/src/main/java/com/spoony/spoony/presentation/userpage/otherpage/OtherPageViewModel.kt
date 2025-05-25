package com.spoony.spoony.presentation.userpage.otherpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.designsystem.theme.main100
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.presentation.userpage.model.ReviewData
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.otherpage.navigation.OtherPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OtherPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userInfo = savedStateHandle.toRoute<OtherPage>()

    private val _state: MutableStateFlow<UserPageState> = MutableStateFlow(
        UserPageState(
            userType = UserType.OTHER_PAGE,
            profile = UserProfile(),
            isLocalReviewOnly = true
        )
    )
    val state: StateFlow<UserPageState>
        get() = _state.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val profile = UserProfile(
                profileId = userInfo.userId,
                imageUrl = "https://avatars.githubusercontent.com/u/52882799?v=4",
                nickname = "맛잘알 패트릭 ${userInfo.userId}",
                region = "경기도 스푼",
                introduction = "그의 네이버 지도를 훔쳐라",
                reviewCount = 2,
                followerCount = 10,
                followingCount = 20,
                isFollowing = false
            )
            _state.update { it.copy(profile = profile) }
            getUserReviews()
        }
    }

    fun toggleFollow() {
        viewModelScope.launch {
            _state.update { state ->
                val currentProfile = state.profile

                when {
                    currentProfile.isBlocked -> state.copy(
                        profile = currentProfile.copy(
                            isBlocked = false
                        )
                    )

                    else -> state.copy(
                        profile = currentProfile.copy(
                            isFollowing = !currentProfile.isFollowing,
                            followerCount = if (currentProfile.isFollowing) {
                                currentProfile.followerCount - 1
                            } else {
                                currentProfile.followerCount + 1
                            }
                        )
                    )
                }
            }
        }
    }

    fun toggleLocalReviewOnly() {
        _state.update { it.copy(isLocalReviewOnly = !it.isLocalReviewOnly) }
    }

    fun blockUser(userId: Int) {
        viewModelScope.launch {
            _state.update { state ->
                val currentProfile = state.profile
                state.copy(
                    profile = currentProfile.copy(
                        isBlocked = !currentProfile.isBlocked
                    )
                )
            }
        }
    }

    private fun getUserReviews() {
        viewModelScope.launch {
            val mockReviews = persistentListOf(
                ReviewData(
                    reviewId = 1,
                    content = "이 근처에서 제일 맛있는 파스타집! 까르보나라가 진짜 크리미하고 맛있어요. 가격도 합리적이에요.",
                    category = ReviewCardCategory(
                        text = "파스타",
                        iconUrl = "",
                        textColor = main400,
                        backgroundColor = main100
                    ),
                    username = "맛잘알 패트릭 ${userInfo.userId}",
                    userRegion = "경기도 스푼",
                    date = "2023.08.10",
                    addMapCount = 15,
                    imageList = persistentListOf(
                        "https://picsum.photos/id/488/200/200"
                    )
                ),
                ReviewData(
                    reviewId = 2,
                    content = "이 동네에 이런 핫도그집이 있다니! 핫도그 빵이 정말 폭신폭신하고 소시지도 쫄깃해요. 소스도 다양해서 취향대로 골라먹을 수 있어요.",
                    category = ReviewCardCategory(
                        text = "브런치",
                        iconUrl = "",
                        textColor = main400,
                        backgroundColor = main100
                    ),
                    username = "맛잘알 패트릭 ${userInfo.userId}",
                    userRegion = "경기도 스푼",
                    date = "2023.07.25",
                    addMapCount = 8,
                    imageList = persistentListOf(
                        "https://picsum.photos/id/431/200/200",
                        "https://picsum.photos/id/555/200/200"
                    )
                )
            )

            _state.update { it.copy(reviews = mockReviews) }
        }
    }
}
