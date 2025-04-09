package com.spoony.spoony.presentation.follow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
import timber.log.Timber

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
        val mockFollowers = (1..50).map { index ->
            UserItemUiState(
                userId = index,
                userName = when (index % 10) {
                    0 -> "김스푸니$index"
                    1 -> "박맛집$index"
                    2 -> "이푸드$index"
                    3 -> "최미식$index"
                    4 -> "정먹방$index"
                    5 -> "황냠냠$index"
                    6 -> "한맛집$index"
                    7 -> "송푸드$index"
                    8 -> "정맛도$index"
                    else -> "윤미식$index"
                },
                imageUrl = "https://picsum.photos/${200 + index}",
                region = when (index % 8) {
                    0 -> "서울"
                    1 -> "부산"
                    2 -> "인천"
                    3 -> "대구"
                    4 -> "대전"
                    5 -> "광주"
                    6 -> "울산"
                    else -> "제주도"
                },
                isFollowing = index % 2 == 0
            )
        }.toImmutableList()

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

    fun refreshFollowers() {
        viewModelScope.launch {
            loadFollowers()
            Timber.d("Followers refreshed")
        }
    }

    fun refreshFollowings() {
        viewModelScope.launch {
            loadFollowings()
            Timber.d("Followers refreshed")
        }
    }

    fun toggleFollowForFollower(userId: Int) {
        toggleFollow(userId, isFollower = true)
    }

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
