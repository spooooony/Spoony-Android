package com.spoony.spoony.presentation.map.locationMap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.USER_ID
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.presentation.map.locationMap.navigation.LocationMap
import com.spoony.spoony.presentation.map.model.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class LocationMapViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val mapRepository: MapRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableStateFlow<LocationMapState> = MutableStateFlow(LocationMapState())
    val state: StateFlow<LocationMapState>
        get() = _state.asStateFlow()

    init {
        getUserInfo()

        with(savedStateHandle.toRoute<LocationMap>()) {
            getAddedPlaceListByLocation(locationId = locationId ?: 0)
            _state.update {
                it.copy(
                    locationModel = LocationModel(
                        placeName = locationName,
                        placeId = locationId,
                        scale = scale?.toDouble() ?: 0.00,
                        latitude = latitude?.toDouble() ?: 0.00,
                        longitude = longitude?.toDouble() ?: 0.00
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

    fun getAddedPlaceListByLocation(locationId: Int) {
        viewModelScope.launch {
            mapRepository.getAddedPlaceListByLocation(
                userId = USER_ID,
                locationId = locationId
            ).onSuccess { response ->
                _state.update {
                    it.copy(
                        addedPlaceList = if (response.isEmpty()) {
                            UiState.Empty
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
}