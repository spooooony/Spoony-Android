package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PlaceDetailViewModel @Inject constructor() : ViewModel() {
    var _state: MutableStateFlow<PlaceDetailState> = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state

    fun updateScoop(isScoop: Boolean) {
        _state.value = _state.value.copy(
            postEntity = when (_state.value.postEntity) {
                is UiState.Success -> {
                    val currentPost = (_state.value.postEntity as UiState.Success<PostEntity>).data
                    UiState.Success(currentPost.copy(isScoop = !isScoop))
                }
                else -> _state.value.postEntity
            }
        )
    }

    fun updateZzim(isZzim: Boolean) {
        _state.value = _state.value.copy(
            postEntity = when (_state.value.postEntity) {
                is UiState.Success -> {
                    val currentPost = (_state.value.postEntity as UiState.Success<PostEntity>).data
                    UiState.Success(currentPost.copy(isZzim = !isZzim))
                }
                else -> _state.value.postEntity
            }
        )
    }
}
