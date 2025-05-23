package com.spoony.spoony.presentation.userpage.otherpage

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.ReviewRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.userpage.model.ReviewData
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.otherpage.navigation.OtherPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
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

    private val _sideEffect = MutableSharedFlow<OtherPageSideEffect>()
    val sideEffect: SharedFlow<OtherPageSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getUserInfoById(userInfo.userId)
                .onSuccess { userInfoEntity ->
                    _state.update { currentState ->
                        currentState.copy(
                            profile = UserProfile(
                                profileId = userInfoEntity.userId,
                                imageUrl = userInfoEntity.profileImageUrl,
                                nickname = userInfoEntity.userName,
                                region = userInfoEntity.regionName,
                                introduction = userInfoEntity.introduction,
                                reviewCount = userInfoEntity.reviewCount,
                                followerCount = userInfoEntity.followerCount,
                                followingCount = userInfoEntity.followingCount,
                                isFollowing = userInfoEntity.isFollowing
                            )
                        )
                    }
                    getOtherUserReviews()
                }
                .onFailure {
                    _sideEffect.emit(OtherPageSideEffect.ShowSnackbar("예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."))
                }
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
        getOtherUserReviews()
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

    private fun getOtherUserReviews() {
        viewModelScope.launch {
            reviewRepository.getOtherReview(userInfo.userId, _state.value.isLocalReviewOnly)
                .onSuccess { reviewEntity ->
                    val reviews = reviewEntity.feedList.map { feed ->
                        ReviewData(
                            reviewId = feed.postId,
                            content = feed.description,
                            category = ReviewCardCategory(
                                text = feed.categoryInfo.categoryName,
                                iconUrl = feed.categoryInfo.iconUrl,
                                textColor = Color.hexToColor(feed.categoryInfo.textColor.toValidHexColor()),
                                backgroundColor = Color.hexToColor(feed.categoryInfo.backgroundColor.toValidHexColor())
                            ),
                            username = feed.userName,
                            userRegion = feed.userRegion,
                            date = feed.createdAt,
                            addMapCount = feed.zzimCount,
                            imageList = feed.photoUrlList.toImmutableList()
                        )
                    }.toImmutableList()

                    _state.update { currentState ->
                        currentState.copy(reviews = reviews)
                    }
                }
                .onFailure {
                    _sideEffect.emit(OtherPageSideEffect.ShowSnackbar("예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }
}

sealed class OtherPageSideEffect {
    data class ShowSnackbar(val message: String) : OtherPageSideEffect()
}
