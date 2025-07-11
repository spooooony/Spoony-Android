package com.spoony.spoony.presentation.userpage.otherpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.ReviewRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.follow.FollowManager
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.model.toModel
import com.spoony.spoony.presentation.userpage.otherpage.navigation.OtherPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OtherPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val followManager: FollowManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userInfo = savedStateHandle.toRoute<OtherPage>()

    private val _state: MutableStateFlow<UserPageState> = MutableStateFlow(
        UserPageState(
            userType = UserType.OTHER_PAGE,
            profile = UserProfile()
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
                        currentState.copy(profile = userInfoEntity.toModel())
                    }
                    getOtherUserReviews()
                }
                .onLogFailure {
                    _sideEffect.emit(OtherPageSideEffect.ShowErrorSnackbar(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    fun toggleFollow() {
        val currentProfile = _state.value.profile
        val isCurrentlyFollowing = currentProfile.isFollowing
        val originalFollowerCount = currentProfile.followerCount

        followManager.toggleFollow(
            userId = currentProfile.profileId,
            isCurrentlyFollowing = isCurrentlyFollowing,
            onUiUpdate = { newFollowState ->
                _state.update { state ->
                    val followerCountDiff = if (newFollowState) 1 else -1
                    state.copy(
                        profile = state.profile.copy(
                            isFollowing = newFollowState,
                            followerCount = originalFollowerCount + followerCountDiff
                        )
                    )
                }
            },
            getCurrentState = { _state.value.profile.isFollowing },
            onError = {
                viewModelScope.launch {
                    _sideEffect.emit(OtherPageSideEffect.ShowErrorSnackbar(ErrorType.UNEXPECTED_ERROR))
                }
            },
            coroutineScope = viewModelScope
        )
    }

    fun toggleLocalReviewOnly() {
        _state.update { it.copy(isLocalReviewOnly = !it.isLocalReviewOnly) }
        getOtherUserReviews()
    }

    fun blockUser(userId: Int) {
        viewModelScope.launch {
            when (_state.value.isBlocked) {
                true -> {
                    userRepository.unblockUser(userId)
                        .onSuccess {
                            _state.update { currentState ->
                                currentState.copy(
                                    profile = currentState.profile.copy(
                                        isBlocked = false
                                    )
                                )
                            }
                            getUserProfile()
                            _sideEffect.emit(OtherPageSideEffect.ShowSnackbar("해당 유저가 차단 해제되었어요."))
                        }
                        .onLogFailure {
                            _sideEffect.emit(OtherPageSideEffect.ShowErrorSnackbar(ErrorType.UNEXPECTED_ERROR))
                        }
                }

                false -> {
                    userRepository.blockUser(userId)
                        .onSuccess {
                            _state.update { currentState ->
                                currentState.copy(
                                    profile = currentState.profile.copy(
                                        isBlocked = true
                                    )
                                )
                            }
                            _sideEffect.emit(OtherPageSideEffect.ShowSnackbar("해당 유저가 차단되었어요."))
                        }
                        .onLogFailure {
                            _sideEffect.emit(OtherPageSideEffect.ShowErrorSnackbar(ErrorType.UNEXPECTED_ERROR))
                        }
                }
            }
        }
    }

    private fun getOtherUserReviews() {
        viewModelScope.launch {
            reviewRepository.getOtherReview(userInfo.userId, _state.value.isLocalReviewOnly)
                .onSuccess { reviewEntity ->
                    _state.update { currentState ->
                        currentState.copy(reviews = reviewEntity.toModel())
                    }
                }
                .onLogFailure {
                    _sideEffect.emit(OtherPageSideEffect.ShowErrorSnackbar(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        followManager.clear()
    }
}

sealed class OtherPageSideEffect {
    data class ShowSnackbar(val message: String) : OtherPageSideEffect()
    data class ShowErrorSnackbar(val errorType: ErrorType) : OtherPageSideEffect()
}
