package com.spoony.spoony.presentation.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.USER_ID
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.presentation.map.model.LocationModel
import com.spoony.spoony.presentation.map.navigaion.Map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val mapRepository: MapRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var _state: MutableStateFlow<MapState> = MutableStateFlow(MapState())
    val state: StateFlow<MapState>
        get() = _state.asStateFlow()

    init {
        getUserInfo()

        with(savedStateHandle.toRoute<Map>()) {
            if (locationId == null) return@with

            getAddedPlaceListByLocation(locationId = locationId)
            _state.update {
                it.copy(
                    locationModel = LocationModel(
                        placeName = locationName,
                        placeId = locationId,
                        scale = scale?.toDouble() ?: state.value.locationModel.scale,
                        latitude = latitude?.toDouble() ?: state.value.locationModel.latitude,
                        longitude = longitude?.toDouble() ?: state.value.locationModel.longitude
                    )
                )
            }
        }
    }

    fun getPlaceInfo(placeId: Int) {
        viewModelScope.launch {
            postRepository.getAddedMapPost(USER_ID, placeId)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            placeCardInfo = UiState.Success(
                                response.toImmutableList()
                            )
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun getAddedPlaceList() {
        viewModelScope.launch {
            mapRepository.getAddedPlaceList(USER_ID)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            placeCount = response.count,
                            addedPlaceList = if (response.count == 0) {
                                UiState.Success(
                                    response.placeList.toImmutableList()
                                )
                            } else {
                                UiState.Success(
                                    response.placeList.toImmutableList()
                                )
                            }
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

    fun getAddedPlaceListByLocation(locationId: Int) {
        viewModelScope.launch {
            mapRepository.getAddedPlaceListByLocation(
                userId = USER_ID,
                locationId = locationId
            ).onSuccess { response ->
                _state.update {
                    it.copy(
                        addedPlaceList = if (response.isEmpty()) {
                            UiState.Success(
                                response.toImmutableList()
                            )
                        } else {
                            UiState.Success(
                                response.toImmutableList()
                            )
                        },
                    )
                }
            }.onFailure(Timber::e)
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

    fun getSpoonCount() {
        viewModelScope.launch {
            authRepository.getSpoonCount(USER_ID)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            spoonCount = response
                        )
                    }
                }
        }
    }

    fun updateLocationModel(locationModel: LocationModel) {
        _state.update {
            it.copy(
                locationModel = locationModel
            )
        }
    }

    fun resetSelectedPlace() {
        _state.update {
            it.copy(
                locationModel = it.locationModel.copy(
                    placeId = null
                )
            )
        }
    }
}
