package com.spoony.spoony.presentation.userpage.otherpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.presentation.userpage.otherpage.navigation.OtherPage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OtherUserProfile(
    val profileId: Int,
    val imageUrl: String,
    val nickname: String,
    val region: String,
    val introduction: String,
    val reviewCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean = false
)

data class OtherPageState(
    val userProfile: OtherUserProfile? = null,
    val isLocalReviewOnly: Boolean = false
)

@HiltViewModel
class OtherPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val _state: MutableStateFlow<OtherPageState> = MutableStateFlow(OtherPageState())
    val state: StateFlow<OtherPageState>
        get() = _state

    private val userInfo = savedStateHandle.toRoute<OtherPage>()
    
    init {
        getUserProfile()
    }
    
    fun getUserProfile() {
        viewModelScope.launch {
            val profile = OtherUserProfile(
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
            _state.update { it.copy(userProfile = profile) }
        }
    }
    
    fun toggleFollow() {
        viewModelScope.launch {
            _state.update { 
                val currentProfile = it.userProfile ?: return@update it
                it.copy(
                    userProfile = currentProfile.copy(
                        isFollowing = !currentProfile.isFollowing,
                        followerCount = if (currentProfile.isFollowing) 
                            currentProfile.followerCount - 1 
                        else 
                            currentProfile.followerCount + 1
                    )
                )
            }
        }
    }
    
    fun toggleLocalReviewOnly() {
        _state.update { it.copy(isLocalReviewOnly = !it.isLocalReviewOnly) }
    }
}
