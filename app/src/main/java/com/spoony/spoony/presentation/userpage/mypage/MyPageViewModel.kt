package com.spoony.spoony.presentation.userpage.mypage

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toValidHexColor
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.ReviewRepository
import com.spoony.spoony.domain.repository.SpoonRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.userpage.model.ReviewData
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val spoonRepository: SpoonRepository,
    private val postRepository: PostRepository
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

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: SharedFlow<MyPageSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess { userInfo ->
                    _state.update { currentState ->
                        currentState.copy(
                            profile = UserProfile(
                                profileId = userInfo.userId,
                                imageUrl = userInfo.profileImageUrl,
                                nickname = userInfo.userName,
                                region = userInfo.regionName,
                                introduction = userInfo.introduction,
                                reviewCount = userInfo.reviewCount,
                                followerCount = userInfo.followerCount,
                                followingCount = userInfo.followingCount,
                                isFollowing = userInfo.isFollowing
                            )
                        )
                    }
                    getMyReview()
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.GENERAL_ERROR))
                }
        }
    }

    fun getSpoonCount() {
        viewModelScope.launch {
            spoonRepository.getSpoonCount()
                .onSuccess { spoonCount ->
                    _state.update { currentState ->
                        currentState.copy(spoonCount = spoonCount)
                    }
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.GENERAL_ERROR))
                }
        }
    }

    private fun getMyReview() {
        viewModelScope.launch {
            reviewRepository.getMyReview()
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
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.GENERAL_ERROR))
                }
        }
    }

    fun deleteReview(reviewId: Int) {
        viewModelScope.launch {
            postRepository.deletePost(reviewId)
                .onSuccess {
                    getUserProfile()
                    _sideEffect.emit(MyPageSideEffect.ShowSnackbar("삭제 되었어요!"))
                }
                .onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowError(ErrorType.GENERAL_ERROR))
                }
        }
    }
}

sealed class MyPageSideEffect {
    data class ShowSnackbar(val message: String) : MyPageSideEffect()
    data class ShowError(val errorType: ErrorType) : MyPageSideEffect()
}
