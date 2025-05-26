package com.spoony.spoony.presentation.follow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.follow.model.FollowType
import com.spoony.spoony.presentation.follow.model.UserItemUiState
import com.spoony.spoony.presentation.follow.model.toModel
import com.spoony.spoony.presentation.follow.navigation.Follow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FollowViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val followManager: FollowManager
) : ViewModel() {

    private val _followers = MutableStateFlow<ImmutableList<UserItemUiState>>(persistentListOf())
    val followers: StateFlow<ImmutableList<UserItemUiState>>
        get() = _followers.asStateFlow()

    private val _following = MutableStateFlow<ImmutableList<UserItemUiState>>(persistentListOf())
    val following: StateFlow<ImmutableList<UserItemUiState>>
        get() = _following.asStateFlow()

    private val _sideEffect = MutableSharedFlow<FollowPageSideEffect>()
    val sideEffect: SharedFlow<FollowPageSideEffect>
        get() = _sideEffect.asSharedFlow()

    private val _followType = MutableStateFlow(FollowType.FOLLOWER)
    val followType: StateFlow<FollowType>
        get() = _followType.asStateFlow()

    private val followInfo = savedStateHandle.toRoute<Follow>()
    private val userId = followInfo.userId

    init {
        _followType.value = followInfo.followType
        loadInitialData()
    }

    private fun loadInitialData() {
        loadFollowers()
        loadFollowings()
    }

    private fun loadFollowers() {
        viewModelScope.launch {
            val result = if (userId == -1) {
                userRepository.getMyFollowers()
            } else {
                userRepository.getOtherFollowers(userId)
            }

            result
                .onSuccess { followList ->
                    _followers.value = followList.users.map { it.toModel() }.toImmutableList()
                }
                .onLogFailure {
                    _followers.value = persistentListOf()
                    _sideEffect.emit(FollowPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun loadFollowings() {
        viewModelScope.launch {
            val result = if (userId == -1) {
                userRepository.getMyFollowings()
            } else {
                userRepository.getOtherFollowings(userId)
            }

            result
                .onSuccess { followList ->
                    _following.value = followList.users.map { it.toModel() }.toImmutableList()
                }
                .onLogFailure {
                    _following.value = persistentListOf()
                    _sideEffect.emit(FollowPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun refreshFollowers() {
        loadFollowers()
    }

    private fun refreshFollowings() {
        loadFollowings()
    }

    fun refresh(type: FollowType) {
        when (type) {
            FollowType.FOLLOWER -> refreshFollowers()
            FollowType.FOLLOWING -> refreshFollowings()
        }
    }

    fun toggleFollow(userId: Int) {
        val targetUser = findTargetUser(userId, _followers.value to _following.value)
        val isCurrentlyFollowing = targetUser?.isFollowing == true

        followManager.toggleFollow(
            userId = userId,
            isCurrentlyFollowing = isCurrentlyFollowing,
            onUiUpdate = { newFollowState ->
                _followers.value = updateFollowState(_followers.value, userId, newFollowState)
                _following.value = updateFollowState(_following.value, userId, newFollowState)
            },
            getCurrentState = {
                findTargetUser(userId, _followers.value to _following.value)?.isFollowing == true
            },
            onError = {
                viewModelScope.launch {
                    _sideEffect.emit(FollowPageSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
            },
            coroutineScope = viewModelScope
        )
    }

    private fun findTargetUser(
        userId: Int,
        states: Pair<ImmutableList<UserItemUiState>, ImmutableList<UserItemUiState>>
    ): UserItemUiState? {
        val (followers, following) = states
        return followers.find { it.userId == userId } ?: following.find { it.userId == userId }
    }

    private fun updateFollowState(
        currentList: ImmutableList<UserItemUiState>,
        userId: Int,
        isFollowing: Boolean
    ): ImmutableList<UserItemUiState> {
        return currentList.map { user ->
            if (user.userId == userId) {
                user.copy(isFollowing = isFollowing)
            } else {
                user
            }
        }.toImmutableList()
    }

    override fun onCleared() {
        super.onCleared()
        followManager.clear()
    }
}

sealed class FollowPageSideEffect {
    data class ShowSnackbar(val message: String) : FollowPageSideEffect()
    data class ShowError(val errorType: ErrorType) : FollowPageSideEffect()
}
