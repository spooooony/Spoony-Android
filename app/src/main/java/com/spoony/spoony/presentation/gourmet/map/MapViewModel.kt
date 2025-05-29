package com.spoony.spoony.presentation.gourmet.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.gourmet.map.model.LocationModel
import com.spoony.spoony.presentation.gourmet.map.model.toReviewCardModel
import com.spoony.spoony.presentation.gourmet.map.model.toReviewModel
import com.spoony.spoony.presentation.gourmet.map.navigaion.Map
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
class MapViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
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
            postRepository.getAddedMapPost(placeId)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            placeCardInfo = UiState.Success(
                                response.map { review -> review.toReviewCardModel() }.toImmutableList()
                            )
                        )
                    }
                }
                .onFailure(Timber::e)
        }
    }

    fun getAddedPlaceList() {
        viewModelScope.launch {
            mapRepository.getAddedPlaceList(1)
                .onSuccess { (count, reviewList) ->
                    _state.update {
                        it.copy(
                            placeCount = count,
                            addedPlaceList = if (count == 0) {
                                UiState.Empty
                            } else {
                                UiState.Success(
                                    reviewList.map { review -> review.toReviewModel() }.toImmutableList()
                                )
                            }
                        )
                    }
                }
                .onLogFailure {
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
                locationId = locationId
            ).onSuccess { (count, reviewList) ->
                _state.update {
                    it.copy(
                        placeCount = count,
                        addedPlaceList = if (count == 0) {
                            UiState.Empty
                        } else {
                            UiState.Success(
                                reviewList.map { review -> review.toReviewModel() }.toImmutableList()
                            )
                        }
                    )
                }
            }.onLogFailure {
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
            userRepository.getMyInfo()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            userName = UiState.Success(response.userName)
                        )
                    }
                }
                .onLogFailure {
                    // TODO: 에러 처리
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
