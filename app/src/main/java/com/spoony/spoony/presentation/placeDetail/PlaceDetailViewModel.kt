package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PlaceDetailViewModel @Inject constructor() : ViewModel() {
    var _state: MutableStateFlow<PlaceDetailState> = MutableStateFlow(PlaceDetailState())
    val state: StateFlow<PlaceDetailState>
        get() = _state

    fun useSpoon() {
        _state.value = _state.value.copy(
            postEntity = when (val currentPost = _state.value.postEntity) {
                is UiState.Success -> {
                    UiState.Success(currentPost.data.copy(isScooped = true))
                }
                else -> currentPost
            }
        )
    }

    fun updateAddMap(isAddMap: Boolean) {
        _state.value = _state.value.copy(
            postEntity = when (val currentPost = _state.value.postEntity) {
                is UiState.Success -> {
                    UiState.Success(currentPost.data.copy(isAddMap = !isAddMap))
                }
                else -> currentPost
            }
        )
    }
}
