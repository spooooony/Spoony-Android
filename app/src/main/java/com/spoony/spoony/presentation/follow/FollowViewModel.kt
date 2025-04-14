package com.spoony.spoony.presentation.follow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import com.spoony.spoony.presentation.follow.navigation.Follow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@HiltViewModel
class FollowViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _followers = MutableStateFlow<ImmutableList<UserItemUiState>>(persistentListOf())
    val followers: StateFlow<ImmutableList<UserItemUiState>> = _followers.asStateFlow()

    private val _following = MutableStateFlow<ImmutableList<UserItemUiState>>(persistentListOf())
    val following: StateFlow<ImmutableList<UserItemUiState>> = _following.asStateFlow()

    private val _isFollowingTab = MutableStateFlow(false)
    val isFollowingTab: StateFlow<Boolean> = _isFollowingTab.asStateFlow()

    private val _followType = MutableStateFlow(FollowType.FOLLOWER)
    val followType: StateFlow<FollowType> = _followType.asStateFlow()

    private val followingMutex = Mutex()
    private val followersMutex = Mutex()

    init {
        loadFollowings()
        loadFollowers()

        with(savedStateHandle.toRoute<Follow>()) {
            _followType.value = this.followType
            _isFollowingTab.value = this.followType == FollowType.FOLLOWING
        }
    }

    fun loadFollowers() {
        viewModelScope.launch {
            val mockFollowers = (1..50).map { index ->
                UserItemUiState(
                    userId = index,
                    userName = getUserNameByIndex(index),
                    imageUrl = "https://picsum.photos/${200 + index}",
                    region = getRegionByIndex(index),
                    isFollowing = index % 2 == 0
                )
            }.toImmutableList()

            followersMutex.withLock {
                _followers.value = mockFollowers
            }
        }
    }

    private fun getUserNameByIndex(index: Int): String = when (index % 10) {
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
    }

    private fun getRegionByIndex(index: Int): String = when (index % 8) {
        0 -> "서울"
        1 -> "부산"
        2 -> "인천"
        3 -> "대구"
        4 -> "대전"
        5 -> "광주"
        6 -> "울산"
        else -> "제주도"
    }

    fun loadFollowings() {
        viewModelScope.launch {
            val mockFollowings = listOf(
                UserItemUiState(
                    userId = 6,
                    userName = "맛집헌터",
                    imageUrl = "https://picsum.photos/205",
                    region = "경기도",
                    isFollowing = true
                )
            ).toImmutableList()

            followingMutex.withLock {
                _following.value = mockFollowings
            }
        }
    }

    fun refreshFollowers() {
        viewModelScope.launch {
            loadFollowers()
        }
    }

    fun refreshFollowings() {
        viewModelScope.launch {
            loadFollowings()
        }
    }

    fun toggleFollowForFollower(userId: Int) {
        viewModelScope.launch {
            followersMutex.withLock {
                toggleFollow(_followers, userId)
            }
        }
    }

    fun toggleFollowForFollowing(userId: Int) {
        viewModelScope.launch {
            followingMutex.withLock {
                toggleFollow(_following, userId)
            }
        }
    }

    private fun toggleFollow(stateFlow: MutableStateFlow<ImmutableList<UserItemUiState>>, userId: Int) {
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
