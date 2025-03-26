package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.placeDetail.model.toModel
import com.spoony.spoony.presentation.placeDetail.navigation.PlaceDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableStateFlow<PlaceDetailState> = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state

    private val _sideEffect = MutableSharedFlow<PlaceDetailSideEffect>()
    val sideEffect: SharedFlow<PlaceDetailSideEffect>
        get() = _sideEffect

    init {
        val postArgs = savedStateHandle.toRoute<PlaceDetail>()
        _state.value = _state.value.copy(
            postId = UiState.Success(data = postArgs.postId)
        )
        getPost(postArgs.postId)
        getUserSpoonCount()
    }

    private fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserInfoById(userId)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            userEntity = UiState.Success(response)
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            userEntity = UiState.Failure("사용자 정보 조회 실패")
                        )
                    }
                }
        }
    }

    private fun getPost(postId: Int) {
        viewModelScope.launch {
            postRepository.getPost(postId = postId)
                .onSuccess { response ->
                    getUserInfo(response.userId)
                    _state.update {
                        it.copy(
                            postModel = UiState.Success(
                                response.toModel()
                            ),
                            isScooped = response.isScooped,
                            isAddMap = response.isAddMap,
                            addMapCount = response.addMapCount
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            postModel = UiState.Failure("게시물 조회 실패")
                        )
                    }
                }
        }
    }

    private fun getUserSpoonCount() {
        viewModelScope.launch {
            authRepository.getSpoonCount()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            spoonCount = UiState.Success(
                                response
                            )
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            spoonCount = UiState.Failure("유저 스푼 개수 조회 실패")
                        )
                    }
                }
        }
    }

    fun useSpoon(postId: Int) {
        viewModelScope.launch {
            postRepository.postScoopPost(postId = postId)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isScooped = true,
                            spoonCount = when (val spoonState = it.spoonCount) {
                                is UiState.Success -> UiState.Success(spoonState.data - 1)
                                else -> spoonState
                            }
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun addMyMap(postId: Int) {
        viewModelScope.launch {
            postRepository.postAddMap(postId = postId)
                .onSuccess {
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("내 지도에 추가되었어요."))
                    _state.update {
                        it.copy(
                            isAddMap = true,
                            addMapCount = it.addMapCount + 1
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun deletePinMap(postId: Int) {
        viewModelScope.launch {
            postRepository.deletePinMap(postId = postId)
                .onSuccess {
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("내 지도에서 삭제되었어요."))
                    _state.update {
                        it.copy(
                            isAddMap = false,
                            addMapCount = it.addMapCount - 1
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }
}
