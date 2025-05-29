package com.spoony.spoony.presentation.setting.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.setting.block.model.BlockUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BlockUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _blockingList = MutableStateFlow<ImmutableList<BlockUserState>>(persistentListOf())
    val blockingList: StateFlow<ImmutableList<BlockUserState>> get() = _blockingList.asStateFlow()

    private val blockRequestQueue = MutableSharedFlow<Pair<Int, Boolean>>(extraBufferCapacity = 64)

    init {
        getBlockingList()
        observeBlockRequests()
    }

    private fun getBlockingList() {
        viewModelScope.launch {
            userRepository.getBlockingList()
                .onSuccess { blockingList ->
                    _blockingList.update {
                        blockingList.users
                            .map { user ->
                                BlockUserState(
                                    userId = user.userId,
                                    userName = user.username,
                                    imageUrl = user.profileImageUrl,
                                    region = user.regionName,
                                    isBlocking = true
                                )
                            }
                            .toPersistentList()
                    }
                }
                .onLogFailure { exception ->
                    // TODO: 에러 뷰 받으면 넣기
                }
        }
    }

    fun onClickBlockButton(userId: Int, currentIsBlocking: Boolean) {
        val newIsBlocking = !currentIsBlocking
        _blockingList.update { list ->
            list.map { user ->
                if (user.userId == userId) user.copy(isBlocking = newIsBlocking) else user
            }.toPersistentList()
        }

        blockRequestQueue.tryEmit(userId to newIsBlocking)
    }

    @OptIn(FlowPreview::class)
    private fun observeBlockRequests() {
        viewModelScope.launch {
            blockRequestQueue
                .debounce(BLOCK_REQUEST_DEBOUNCE_TIME)
                .collect { (userId, newIsBlocking) ->
                    val result = if (newIsBlocking) {
                        userRepository.blockUser(userId)
                    } else {
                        userRepository.unblockUser(userId)
                    }

                    result.onLogFailure {
                        _blockingList.update { list ->
                            list.map { user ->
                                if (user.userId == userId) user.copy(isBlocking = !newIsBlocking) else user
                            }.toPersistentList()
                        }
                    }
                }
        }
    }

    companion object {
        const val BLOCK_REQUEST_DEBOUNCE_TIME = 500L // ms
    }
}
