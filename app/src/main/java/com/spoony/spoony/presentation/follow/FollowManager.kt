package com.spoony.spoony.presentation.follow

import com.spoony.spoony.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FollowManager @Inject constructor(
    private val userRepository: UserRepository
) {
    private val debounceJobs = mutableMapOf<Int, Job>()
    private val originalStates = mutableMapOf<Int, Boolean>() // 최초 상태 기억

    fun toggleFollow(
        userId: Int,
        isCurrentlyFollowing: Boolean,
        onUiUpdate: (Boolean) -> Unit,
        getCurrentState: () -> Boolean,
        onError: () -> Unit,
        coroutineScope: CoroutineScope
    ) {
        val newFollowState = !isCurrentlyFollowing
        onUiUpdate(newFollowState)

        if (!originalStates.containsKey(userId)) {
            originalStates[userId] = isCurrentlyFollowing
        }

        debounceJobs[userId]?.cancel()
        debounceJobs[userId] = coroutineScope.launch {
            delay(600)

            val originalState = originalStates[userId] ?: isCurrentlyFollowing
            val finalState = getCurrentState()

            if (originalState == finalState) {
                debounceJobs.remove(userId)
                originalStates.remove(userId)
                return@launch
            }

            val apiResult = if (finalState) userRepository.followUser(userId) else userRepository.unfollowUser(userId)

            apiResult.onFailure {
                onUiUpdate(originalState)
                onError()
            }

            debounceJobs.remove(userId)
            originalStates.remove(userId)
        }
    }

    fun clear() {
        debounceJobs.values.forEach { it.cancel() }
        debounceJobs.clear()
        originalStates.clear()
    }
}
