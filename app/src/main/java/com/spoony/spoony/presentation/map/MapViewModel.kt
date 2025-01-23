package com.spoony.spoony.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.USER_ID
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private var _state: MutableStateFlow<MapState> = MutableStateFlow(MapState())
    val state: StateFlow<MapState>
        get() = _state.asStateFlow()

    init {
        getUserInfo()
        getAddedPlaceList()
    }

    private fun getAddedPlaceList() {
        viewModelScope.launch {
            mapRepository.getAddedPlaceList(USER_ID)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            addedPlaceList = UiState.Success(
                                response.toImmutableList()
                            )
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            addedPlaceList = UiState.Failure("지도 장소 리스트 조회 실패")
                        )
                    }
                }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            authRepository.getUserInfo(USER_ID)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            userName = UiState.Success(response.userName)
                        )
                    }
                }
        }
    }
}
