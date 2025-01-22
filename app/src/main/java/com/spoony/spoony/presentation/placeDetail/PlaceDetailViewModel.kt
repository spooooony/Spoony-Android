package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.presentation.placeDetail.navigation.PlaceDetail
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
    private var _state: MutableStateFlow<PlaceDetailState> = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state

    init {
        val postArgs = savedStateHandle.toRoute<PlaceDetail>()
        _state.value = _state.value.copy(
            postId = UiState.Success(data = postArgs.postId),
            userId = UiState.Success(data = postArgs.userId)
        )
        getPost(postArgs.postId)
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
                    when (response.success) {
                        true -> {
                            (_state.value.postEntity as? UiState.Success)?.data?.let { currentPostEntity ->
                                with(currentPostEntity) {
                                    _state.value = _state.value.copy(
                                        postEntity = UiState.Success(
                                            copy(isScooped = true)
                                        )
                                    )
                                }
                            }
                        }
                        false -> {
                            // 통신에 성공했지만 떠먹기에 실패했을 경우
                        }
                    }
                }
                .onFailure {
                    // 통신에 실패 했을 경우
                }
        }
    }

    fun addMyMap(userId: Int, postId: Int) {
        viewModelScope.launch {
            postRepository.postAddMap(userId = userId, postId = postId)
                .onSuccess { response ->
                    (_state.value.postEntity as? UiState.Success)?.data?.let { currentPostEntity ->
                        with(currentPostEntity) {
                            _state.value = _state.value.copy(
                                postEntity = UiState.Success(
                                    copy(
                                        isAddMap = true,
                                        addMapCount = currentPostEntity.addMapCount + 1
                                    )
                                )
                            )
                        }
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
                    (_state.value.postEntity as? UiState.Success)?.data?.let { currentPostEntity ->
                        with(currentPostEntity) {
                            _state.value = _state.value.copy(
                                postEntity = UiState.Success(
                                    copy(
                                        isAddMap = false,
                                        addMapCount = currentPostEntity.addMapCount - 1
                                    )
                                )
                            )
                        }
                    }
                }
                .onFailure {
                    // 실패 했을 경우
                }
        }
    }
}
