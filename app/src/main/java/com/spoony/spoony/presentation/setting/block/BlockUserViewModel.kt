package com.spoony.spoony.presentation.setting.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.setting.block.model.BlockUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BlockUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _blockingList = MutableStateFlow<UiState<ImmutableList<BlockUserState>>>(UiState.Empty)
    val blockingList: StateFlow<UiState<ImmutableList<BlockUserState>>> get() = _blockingList.asStateFlow()

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: SharedFlow<String>
        get() = _errorEvent.asSharedFlow()

    private val blockRequestJobs = mutableMapOf<Int, Job>()

    private val blockRequestQueue = MutableSharedFlow<Pair<Int, Boolean>>(extraBufferCapacity = 64)

    init {
        getBlockingList()
        observeBlockRequests()
    }

    fun getBlockingList() {
        viewModelScope.launch {
            _blockingList.value = UiState.Loading

            userRepository.getBlockingList()
                .onSuccess { blockingList ->
                    if (blockingList.users.isEmpty()) {
                        _blockingList.value = UiState.Empty
                        return@onSuccess
                    }
                    _blockingList.update {
                        UiState.Success(
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
                        )
                    }
                }
                .onLogFailure { exception ->
                    // TODO: 에러 뷰 받으면 넣기
                    _blockingList.value = UiState.Failure(exception.toString())
                    _errorEvent.emit(ErrorType.SERVER_CONNECTION_ERROR.description)
                }
        }
    }

    fun onClickBlockButton(userId: Int, currentIsBlocking: Boolean) {
        val newIsBlocking = !currentIsBlocking
        _blockingList.update { list ->
            if (list !is UiState.Success) return@update list

            UiState.Success(
                list.data.map { user ->
                    if (user.userId == userId) user.copy(isBlocking = newIsBlocking) else user
                }.toPersistentList()
            )
        }

        blockRequestQueue.tryEmit(userId to newIsBlocking)
    }

    private fun observeBlockRequests() {
        viewModelScope.launch {
            blockRequestQueue.collect { (userId, newIsBlocking) ->
                blockRequestJobs[userId]?.cancel()

                blockRequestJobs[userId] = viewModelScope.launch {
                    delay(BLOCK_REQUEST_DEBOUNCE_TIME)

                    val result = if (newIsBlocking) {
                        userRepository.blockUser(userId)
                    } else {
                        userRepository.unblockUser(userId)
                    }

                    result.onLogFailure {
                        _blockingList.update { list ->
                            if (list !is UiState.Success) return@update list
                            UiState.Success(
                                list.data.map { user ->
                                    if (user.userId == userId) user.copy(isBlocking = !newIsBlocking) else user
                                }.toPersistentList()
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val BLOCK_REQUEST_DEBOUNCE_TIME = 500L // ms
    }
}
