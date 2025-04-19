package com.spoony.spoony.presentation.userpage.otherpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.presentation.userpage.model.UserPageState
import com.spoony.spoony.presentation.userpage.model.UserProfile
import com.spoony.spoony.presentation.userpage.model.UserType
import com.spoony.spoony.presentation.userpage.otherpage.navigation.OtherPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
            isLocalReviewOnly = false
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
                reviewCount = 0,
                followerCount = 10,
                followingCount = 20,
                isFollowing = false
            )
            _state.update { it.copy(profile = profile) }
        }
    }

    fun toggleFollow() {
        viewModelScope.launch {
            _state.update { state ->
                val currentProfile = state.profile
                state.copy(
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

    fun toggleLocalReviewOnly() {
        _state.update { it.copy(isLocalReviewOnly = !it.isLocalReviewOnly) }
    }
}
