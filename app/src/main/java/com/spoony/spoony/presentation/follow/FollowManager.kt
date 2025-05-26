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
        onSuccess: () -> Unit = {},
        debounceDuration: Long = 600L,
        coroutineScope: CoroutineScope
    ) {
        val newFollowState = !isCurrentlyFollowing
        onUiUpdate(newFollowState)

        originalStates.putIfAbsent(userId, isCurrentlyFollowing)

        debounceJobs.remove(userId)?.cancel()
        debounceJobs[userId] = coroutineScope.launch {
            delay(debounceDuration)

            val originalState = originalStates[userId] ?: isCurrentlyFollowing
            val finalState = getCurrentState()

            if (originalState == finalState) {
                cleanupUser(userId)
                return@launch
            }

            val apiResult = if (finalState) {
                userRepository.followUser(userId)
            } else {
                userRepository.unfollowUser(userId)
            }

            apiResult.fold(
                onSuccess = { onSuccess() },
                onFailure = {
                    onUiUpdate(originalState)
                    onError()
                }
            )

            cleanupUser(userId)
        }
    }

    private fun cleanupUser(userId: Int) {
        debounceJobs.remove(userId)
        originalStates.remove(userId)
    }

    fun clear() {
        debounceJobs.values.forEach(Job::cancel)
        debounceJobs.clear()
        originalStates.clear()
    }
}
