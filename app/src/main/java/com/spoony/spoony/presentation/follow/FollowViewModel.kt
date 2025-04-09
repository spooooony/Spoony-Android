package com.spoony.spoony.presentation.follow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import com.spoony.spoony.presentation.follow.navigation.Follow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _followers = MutableStateFlow<ImmutableList<UserItemUiState>>(kotlinx.collections.immutable.persistentListOf())
    val followers: StateFlow<ImmutableList<UserItemUiState>> = _followers.asStateFlow()

    private val _following = MutableStateFlow<ImmutableList<UserItemUiState>>(kotlinx.collections.immutable.persistentListOf())
    val following: StateFlow<ImmutableList<UserItemUiState>> = _following.asStateFlow()

    private val _isFollowingTab = MutableStateFlow(false)
    val isFollowingTab: StateFlow<Boolean> = _isFollowingTab.asStateFlow()

    init {
        loadFollowers()
        loadFollowings()

        with(savedStateHandle.toRoute<Follow>()) {
            _isFollowingTab.value = this.isFollowing
        }
    }

    fun loadFollowers() {
        val mockFollowers = listOf(
            UserItemUiState(
                userId = 1,
                userName = "김스푸니",
                imageUrl = "https://picsum.photos/200",
                region = "서울",
                isFollowing = false
            ),
            UserItemUiState(
                userId = 2,
                userName = "박맛집",
                imageUrl = "https://picsum.photos/201",
                region = "부산",
                isFollowing = true
            ),
            UserItemUiState(
                userId = 3,
                userName = "이푸드",
                imageUrl = "https://picsum.photos/202",
                region = "인천",
                isFollowing = false
            ),
            UserItemUiState(
                userId = 4,
                userName = "최미식",
                imageUrl = "https://picsum.photos/203",
                region = "대구",
                isFollowing = true
            ),
            UserItemUiState(
                userId = 5,
                userName = "정먹방",
                imageUrl = "https://picsum.photos/204",
                region = "대전",
                isFollowing = false
            )
        ).toImmutableList()
        
        _followers.value = mockFollowers
    }

    // 팔로잉 데이터 로드
    fun loadFollowings() {
        val mockFollowings = listOf(
            UserItemUiState(
                userId = 6,
                userName = "맛집헌터",
                imageUrl = "https://picsum.photos/205",
                region = "경기도",
                isFollowing = true
            ),
            UserItemUiState(
                userId = 7,
                userName = "푸드블로거",
                imageUrl = "https://picsum.photos/206",
                region = "강원도",
                isFollowing = true
            ),
            UserItemUiState(
                userId = 8,
                userName = "맛있는여행",
                imageUrl = "https://picsum.photos/207",
                region = "제주도",
                isFollowing = true
            ),
            UserItemUiState(
                userId = 9,
                userName = "맛집리뷰어",
                imageUrl = "https://picsum.photos/208",
                region = "울산",
                isFollowing = true
            )
        ).toImmutableList()
        
        _following.value = mockFollowings
    }

    // 팔로워 팔로우 상태 변경
    fun toggleFollowForFollower(userId: Int) {
        toggleFollow(userId, isFollower = true)
    }

    // 팔로잉 팔로우 상태 변경
    fun toggleFollowForFollowing(userId: Int) {
        toggleFollow(userId, isFollower = false)
    }

    private fun toggleFollow(userId: Int, isFollower: Boolean) {
        val stateFlow = if (isFollower) _followers else _following

        stateFlow.update { currentList ->
            currentList.map { user ->
                if (user.userId == userId) {
                    user.copy(isFollowing = !user.isFollowing)
                } else {
                    user
                }
            }.toImmutableList()
        }
    }
}
