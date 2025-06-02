package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.SpoonRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.placeDetail.model.toModel
import com.spoony.spoony.presentation.placeDetail.navigation.PlaceDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

@OptIn(FlowPreview::class)
@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val spoonRepository: SpoonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableStateFlow<PlaceDetailState> = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PlaceDetailSideEffect>()
    val sideEffect: SharedFlow<PlaceDetailSideEffect>
        get() = _sideEffect.asSharedFlow()

    private val _followClickFlow = MutableSharedFlow<Pair<Int, Boolean>>(extraBufferCapacity = 1)

    init {
        val postArgs = savedStateHandle.toRoute<PlaceDetail>()
        _state.value = _state.value.copy(
            reviewId = UiState.Success(data = postArgs.postId)
        )
        getPost(postArgs.postId)
        getUserSpoonCount()

        viewModelScope.launch {
            _followClickFlow
                .debounce(300)
                .collect { (userId, isFollowing) ->
                    followClick(userId, isFollowing)
                }
        }
    }

    fun onFollowButtonClick(userId: Int, isFollowing: Boolean) {
        _state.update {
            it.copy(isFollowing = !isFollowing)
        }
        _followClickFlow.tryEmit(Pair(userId, isFollowing))
    }

    private fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserInfoById(userId)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            userInfo = UiState.Success(
                                response.toModel()
                            ),
                            isFollowing = response.isFollowing
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            userInfo = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                        )
                    }
                }
        }
    }

    private fun followClick(userId: Int, isFollowing: Boolean) {
        viewModelScope.launch {
            val result = if (isFollowing) {
                userRepository.unfollowUser(userId = userId)
            } else {
                userRepository.followUser(userId = userId)
            }
            result.onFailure { throwable ->
                if (throwable is HttpException && throwable.code() == 403) {
                    return@onFailure
                }
                _state.update {
                    it.copy(isFollowing = !it.isFollowing)
                }
                _sideEffect.emit(
                    PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                )
            }
        }
    }

    private fun getPost(postId: Int) {
        viewModelScope.launch {
            postRepository.getPost(postId = postId)
                .onSuccess { response ->
                    getUserInfo(response.userId ?: -1)
                    _state.update {
                        it.copy(
                            placeDetailModel = UiState.Success(
                                response.toModel()
                            ),
                            isScooped = response.isScooped ?: false,
                            isAddMap = response.isAddMap ?: false,
                            addMapCount = response.addMapCount ?: 0
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            placeDetailModel = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                        )
                    }
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    private fun getUserSpoonCount() {
        viewModelScope.launch {
            spoonRepository.getSpoonCount()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            spoonCount = UiState.Success(
                                response
                            )
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e)
                    _state.update {
                        it.copy(
                            spoonCount = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                        )
                    }
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
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
                .onFailure { e ->
                    Timber.e(e)
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    fun addMyMap(postId: Int) {
        _state.update {
            it.copy(
                isAddMap = true,
                addMapCount = it.addMapCount + 1
            )
        }
        viewModelScope.launch {
            postRepository.postAddMap(postId = postId)
                .onSuccess {
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("내 지도에 추가되었어요."))
                }
                .onFailure { e ->
                    Timber.e(e)
                    _state.update {
                        it.copy(
                            isAddMap = false,
                            addMapCount = it.addMapCount - 1
                        )
                    }
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    fun deletePinMap(postId: Int) {
        _state.update {
            it.copy(
                isAddMap = false,
                addMapCount = it.addMapCount - 1
            )
        }
        viewModelScope.launch {
            postRepository.deletePinMap(postId = postId)
                .onSuccess {
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("내 지도에서 삭제되었어요."))
                }
                .onFailure { e ->
                    Timber.e(e)
                    _state.update {
                        it.copy(
                            isAddMap = true,
                            addMapCount = it.addMapCount + 1
                        )
                    }
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    fun deleteReview(
        postId: Int
    ) {
        viewModelScope.launch {
            postRepository.deletePost(postId)
                .onSuccess {
                    _sideEffect.emit(PlaceDetailSideEffect.NavigateUp)
                }
                .onFailure { e ->
                    Timber.e(e)
                    _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    fun showSnackBar(text: String) {
        viewModelScope.launch {
            _sideEffect.emit(PlaceDetailSideEffect.ShowSnackbar(text))
        }
    }
}
