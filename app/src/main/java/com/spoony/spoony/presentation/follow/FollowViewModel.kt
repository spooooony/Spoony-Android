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

    private val _followType = MutableStateFlow(FollowType.FOLLOWER)
    val followType: StateFlow<FollowType> = _followType.asStateFlow()

    private val followingMutex = Mutex()
    private val followersMutex = Mutex()

    private val followInfo = savedStateHandle.toRoute<Follow>()
    private val userId = followInfo.userId

    init {
        _followType.value = followInfo.followType
        loadInitialData()
    }

    private fun loadInitialData() {
        loadFollowers(userId)
        loadFollowings(userId)
    }

    fun loadFollowers(userId: Int) {
        viewModelScope.launch {
            followersMutex.withLock {
                _followers.value = generateMockFollowers(userId)
            }
        }
    }

    private fun generateMockFollowers(userId: Int): ImmutableList<UserItemUiState> {
        return (1..50).map { index ->
            UserItemUiState(
                userId = index + userId,
                userName = getUserNameByIndex(index + userId),
                imageUrl = "https://picsum.photos/${200 + index}",
                region = getRegionByIndex(index + userId),
                isFollowing = index % 2 == 0
            )
        }.toImmutableList()
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

    fun loadFollowings(userId: Int) {
        viewModelScope.launch {
            followingMutex.withLock {
                _following.value = generateMockFollowings(userId)
            }
        }
    }

    private fun generateMockFollowings(userId: Int): ImmutableList<UserItemUiState> {
        return (1..50).map { index ->
            UserItemUiState(
                userId = index + userId,
                userName = getUserNameByIndex(index + userId),
                imageUrl = "https://picsum.photos/${200 + index}",
                region = getRegionByIndex(index + userId),
                isFollowing = index % 2 == 0
            )
        }.toImmutableList()
    }

    fun refreshFollowers() {
        loadFollowers(userId)
    }

    fun refreshFollowings() {
        loadFollowings(userId)
    }

    fun refresh(type: FollowType) {
        when (type) {
            FollowType.FOLLOWER -> refreshFollowers()
            FollowType.FOLLOWING -> refreshFollowings()
        }
    }

    fun toggleFollow(userId: Int) {
        viewModelScope.launch {
            followersMutex.withLock {
                val updatedList = toggleFollow(_followers.value, userId)
                _followers.value = updatedList
            }
            followingMutex.withLock {
                val updatedList = toggleFollow(_following.value, userId)
                _following.value = updatedList
            }
        }
    }

    private fun toggleFollow(currentList: ImmutableList<UserItemUiState>, userId: Int): ImmutableList<UserItemUiState> {
        return currentList.map { user ->
            if (user.userId == userId) {
                user.copy(isFollowing = !user.isFollowing)
            } else {
                user
            }
        }.toImmutableList()
    }
}
