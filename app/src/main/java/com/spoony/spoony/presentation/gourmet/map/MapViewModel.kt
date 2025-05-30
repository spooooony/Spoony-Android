package com.spoony.spoony.presentation.gourmet.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.core.util.extension.toHyphenDate
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.SpoonRepository
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.gourmet.map.model.LocationModel
import com.spoony.spoony.presentation.gourmet.map.navigaion.Map
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MapViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    private val spoonRepository: SpoonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _state: MutableStateFlow<MapState> = MutableStateFlow(MapState())
    val state: StateFlow<MapState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<MapSideEffect> = MutableSharedFlow<MapSideEffect>()
    val sideEffect: SharedFlow<MapSideEffect>
        get() = _sideEffect.asSharedFlow()

    private var _showSpoonDraw: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showSpoonDraw get() = _showSpoonDraw.asStateFlow()

    init {
        getUserInfo()
        checkSpoonDrawn()

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
            mapRepository.getAddedPlaceList()
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
                locationId = locationId
            ).onSuccess { response ->
                _state.update {
                    it.copy(
                        placeCount = response.size,
                        addedPlaceList = if (response.isEmpty()) {
                            UiState.Success(
                                response.toImmutableList()
                            )
                        } else {
                            UiState.Success(
                                response.toImmutableList()
                            )
                        }
                    )
                }
            }.onFailure(Timber::e)
        }
    }

    suspend fun drawSpoon(): SpoonDrawModel {
        spoonRepository.drawSpoon().onSuccess { spoon ->
            spoonRepository.updateSpoonDrawn()

            with(spoon) {
                return SpoonDrawModel(
                    drawId = drawId,
                    spoonTypeId = spoonType.spoonTypeId,
                    spoonName = spoonType.spoonName,
                    spoonImage = spoonType.spoonImage,
                    spoonAmount = spoonType.spoonAmount,
                    localDate = localDate
                )
            }
        }.onLogFailure {
            _sideEffect.emit(MapSideEffect.ShowSnackBar(ErrorType.SERVER_CONNECTION_ERROR.description))
        }
        return SpoonDrawModel.DEFAULT
    }

    fun checkSpoonDrawn() {
        viewModelScope.launch {
            val lastEntryDate = spoonRepository.getSpoonDrawLog().first
            val today = LocalDate.now()

            val shouldShowSpoon = try {
                val parsedDate = LocalDate.parse(lastEntryDate)
                parsedDate != today
            } catch (e: Exception) {
                true
            }

            _showSpoonDraw.update { shouldShowSpoon }
        }
    }

    fun updateLastEntryDate() {
        val today = LocalDate.now()
        viewModelScope.launch {
            spoonRepository.updateLastEntryDate(today.toHyphenDate())
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
