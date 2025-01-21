package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var _state: MutableStateFlow<PlaceDetailState> = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state

    private val postId: Int = savedStateHandle.get<Int>("postId") ?: -1

    init {
        getPost(postId)
    }

    fun getPost(postId: Int) {
        viewModelScope.launch {
            postRepository.getPost(postId = postId)
                .onSuccess { response ->
                    _state.value = _state.value.copy(
                        postEntity = UiState.Success(response)
                    )
                }
                .onFailure {
                    // 실패 했을 경우
                }
        }
    }

    fun useSpoon(userId: Int, postId: Int) {
        viewModelScope.launch {
            postRepository.postScoopPost(userId = userId, postId = postId)
                .onSuccess { response ->
                    val currentPostEntity = (_state.value.postEntity as? UiState.Success)?.data
                    if (currentPostEntity != null) {
                        _state.value = _state.value.copy(
                            postEntity = UiState.Success(
                                currentPostEntity.copy(
                                    isScooped = true
                                )
                            )
                        )
                    }
                }
                .onFailure {
                    // 실패 했을 경우
                }
        }
    }

    fun addMyMap(userId: Int, postId: Int) {
        viewModelScope.launch {
            postRepository.postAddMap(userId = userId, postId = postId)
                .onSuccess { response ->
                    val currentPostEntity = (_state.value.postEntity as? UiState.Success)?.data
                    if (currentPostEntity != null) {
                        _state.value = _state.value.copy(
                            postEntity = UiState.Success(
                                currentPostEntity.copy(
                                    isAddMap = true,
                                    addMapCount = currentPostEntity.addMapCount + 1
                                )
                            )
                        )
                    }
                }
                .onFailure {
                    // 실패 했을 경우
                }
        }
    }

    fun deletePinMap(userId: Int, postId: Int) {
        viewModelScope.launch {
            postRepository.deletePinMap(userId = userId, postId = postId)
                .onSuccess { response ->
                    val currentPostEntity = (_state.value.postEntity as? UiState.Success)?.data
                    if (currentPostEntity != null) {
                        _state.value = _state.value.copy(
                            postEntity = UiState.Success(
                                currentPostEntity.copy(
                                    isAddMap = false,
                                    addMapCount = currentPostEntity.addMapCount - 1
                                )
                            )
                        )
                    }
                }
                .onFailure {
                    // 실패 했을 경우
                }
        }
    }
}
