package com.spoony.spoony.presentation.profileedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.core.util.extension.toBirthDate
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.presentation.profileedit.model.toEntity
import com.spoony.spoony.presentation.profileedit.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileEditState())
    val state: StateFlow<ProfileEditState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileEditSideEffect>()
    val sideEffect: SharedFlow<ProfileEditSideEffect>
        get() = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            val profileImageDeferred = async { getProfileImageInfo() }
            val profileInfoDeferred = async { getMyProfileInfo() }
            val regionListDeferred = async { getRegionList() }

            profileImageDeferred.await()
            profileInfoDeferred.await()
            regionListDeferred.await()
        }
    }

    private fun getProfileImageInfo() {
        viewModelScope.launch {
            userRepository.getMyProfileImage()
                .onSuccess { profileImageEntity ->
                    val profileImageModels = profileImageEntity.toModel(_state.value.selectedImageLevel)
                    _state.update { it.copy(profileImages = profileImageModels) }
                }
                .onLogFailure {
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun getMyProfileInfo() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            userRepository.getMyProfileInfo()
                .onSuccess { profileInfo ->
                    val editModel = profileInfo.toModel()
                    val birthDate = editModel.birth?.toBirthDate()

                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            profileInfo = editModel,
                            nickname = editModel.userName,
                            introduction = editModel.introduction,
                            selectedImageLevel = editModel.imageLevel,
                            selectedYear = birthDate?.year,
                            selectedMonth = birthDate?.month,
                            selectedDay = birthDate?.day,
                            isBirthSelected = birthDate != null,
                            selectedRegion = editModel.regionName,
                            selectedRegionId = editModel.regionId,
                            isRegionSelected = editModel.regionName != null
                        )
                    }

                    if (_state.value.regionList.isNotEmpty() && editModel.regionId != null) {
                        matchRegionIdForInitialRegion(_state.value.regionList)
                    }
                }
                .onLogFailure {
                    _state.update { it.copy(isLoading = false) }
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun getRegionList() {
        viewModelScope.launch {
            userRepository.getRegionList()
                .onSuccess { regionEntities ->
                    val regionModels = regionEntities.map { entity ->
                        RegionModel(
                            regionId = entity.regionId,
                            regionName = entity.regionName
                        )
                    }.toImmutableList()
                    _state.update { it.copy(regionList = regionModels) }

                    if (_state.value.selectedRegion?.isNotEmpty() == true && _state.value.selectedRegionId != null) {
                        matchRegionIdForInitialRegion(regionModels)
                    }
                }
                .onLogFailure {
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun matchRegionIdForInitialRegion(regionList: List<RegionModel>) {
        _state.update { it.copy(saveButtonEnabled = false) }
        val regionName = _state.value.selectedRegion

        if (regionName.isNullOrBlank()) return

        val districtName = regionName.replace("서울 ", "")

        val matchingRegion = regionList.find { it.regionName == districtName }

        if (matchingRegion != null) {
            _state.update { state ->
                state.copy(
                    selectedRegionId = matchingRegion.regionId,
                    saveButtonEnabled = true
                )
            }
        }
    }

    fun updateNickname(nickname: String) {
        _state.update { it.copy(nickname = nickname) }
        updateSaveButtonState()
    }

    fun updateNicknameState(state: NicknameTextFieldState) {
        if (state == NicknameTextFieldState.DUPLICATE) {
            _state.update {
                it.copy(
                    nicknameState = state,
                    saveButtonEnabled = false
                )
            }
        } else {
            _state.update { it.copy(nicknameState = state) }
            updateSaveButtonState()
        }
    }

    fun checkNicknameDuplication() {
        val currentNickname = _state.value.nickname

        if (currentNickname.isBlank()) {
            _state.update { it.copy(nicknameState = NicknameTextFieldState.NICKNAME_REQUIRED) }
            return
        }

        if (currentNickname == _state.value.profileInfo?.userName) {
            _state.update { it.copy(nicknameState = NicknameTextFieldState.AVAILABLE) }
            return
        }

        viewModelScope.launch {
            userRepository.checkUserNameExist(currentNickname)
                .onSuccess { isDuplicated ->
                    if (isDuplicated) {
                        _state.update {
                            it.copy(
                                nicknameState = NicknameTextFieldState.DUPLICATE,
                                saveButtonEnabled = false
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(nicknameState = NicknameTextFieldState.AVAILABLE)
                        }
                    }
                    updateSaveButtonState()
                }
                .onLogFailure {
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    fun updateIntroduction(introduction: String) {
        _state.update { it.copy(introduction = introduction.takeIf { it.isNotBlank() }) }
    }

    fun selectImageLevel(level: Int) {
        _state.update { currentState ->
            val updatedImages = currentState.profileImages.map { image ->
                image.copy(isSelected = image.imageLevel == level)
            }.toImmutableList()

            currentState.copy(
                selectedImageLevel = level,
                profileImages = updatedImages
            )
        }
    }

    fun selectDate(year: String, month: String, day: String) {
        _state.update {
            it.copy(
                selectedYear = year,
                selectedMonth = month,
                selectedDay = day,
                isBirthSelected = true
            )
        }
    }

    fun selectRegion(regionId: Int, regionName: String) {
        _state.update {
            it.copy(
                selectedRegionId = regionId,
                selectedRegion = "서울 $regionName",
                isRegionSelected = true
            )
        }
    }

    fun updateProfileInfo() {
        _state.update { it.copy(saveButtonEnabled = false) }
        viewModelScope.launch {
            val currentState = _state.value
            val birthDate = if (currentState.isBirthSelected && currentState.selectedYear != null && currentState.selectedMonth != null && currentState.selectedDay != null) {
                "${currentState.selectedYear}-${currentState.selectedMonth.padStart(2, '0')}-${currentState.selectedDay.padStart(2, '0')}"
            } else {
                null
            }

            val editModel = currentState.profileInfo?.copy(
                userName = currentState.nickname,
                introduction = currentState.introduction,
                birth = birthDate,
                regionId = if (currentState.isRegionSelected) currentState.selectedRegionId else null,
                imageLevel = currentState.selectedImageLevel
            ) ?: return@launch

            userRepository.updateMyProfileInfo(editModel.toEntity())
                .onSuccess {
                    _sideEffect.emit(ProfileEditSideEffect.ShowSnackBar("프로필이 저장되었습니다."))
                    _sideEffect.emit(ProfileEditSideEffect.NavigateBack)
                }
                .onLogFailure {
                    _state.update { it.copy(saveButtonEnabled = true) }
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun updateSaveButtonState() {
        val isNicknameValid = when (_state.value.nicknameState) {
            NicknameTextFieldState.DEFAULT -> true
            NicknameTextFieldState.AVAILABLE -> true
            NicknameTextFieldState.DUPLICATE -> false
            else -> false
        }
        _state.update { it.copy(saveButtonEnabled = isNicknameValid) }
    }
}

sealed class ProfileEditSideEffect {
    data class ShowSnackBar(val message: String) : ProfileEditSideEffect()
    data class ShowError(val errorType: ErrorType) : ProfileEditSideEffect()
    object NavigateBack : ProfileEditSideEffect()
}
