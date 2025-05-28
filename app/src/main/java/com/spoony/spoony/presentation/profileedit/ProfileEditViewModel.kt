package com.spoony.spoony.presentation.profileedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.usecase.CheckNicknameDuplicationUseCase
import com.spoony.spoony.domain.usecase.FormatBirthDateUseCase
import com.spoony.spoony.domain.usecase.GetMyProfileImageUseCase
import com.spoony.spoony.domain.usecase.GetMyProfileInfoUseCase
import com.spoony.spoony.domain.usecase.GetRegionListUseCase
import com.spoony.spoony.domain.usecase.ProcessProfileEditDataUseCase
import com.spoony.spoony.domain.usecase.UpdateProfileInfoUseCase
import com.spoony.spoony.presentation.profileedit.model.toEntity
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
    private val getMyProfileImageUseCase: GetMyProfileImageUseCase,
    private val getMyProfileInfoUseCase: GetMyProfileInfoUseCase,
    private val getRegionListUseCase: GetRegionListUseCase,
    private val processProfileEditDataUseCase: ProcessProfileEditDataUseCase,
    private val checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase,
    private val formatBirthDateUseCase: FormatBirthDateUseCase,
    private val updateProfileInfoUseCase: UpdateProfileInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileEditState())
    val state: StateFlow<ProfileEditState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileEditSideEffect>()
    val sideEffect: SharedFlow<ProfileEditSideEffect>
        get() = _sideEffect.asSharedFlow()

    init {
        loadProfileEditData()
    }

    private fun loadProfileEditData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                val profileImageDeferred = async { getMyProfileImageUseCase().getOrThrow() }
                val profileInfoDeferred = async { getMyProfileInfoUseCase().getOrThrow() }
                val regionListDeferred = async { getRegionListUseCase().getOrThrow() }

                val processedData = processProfileEditDataUseCase(
                    profileImage = profileImageDeferred.await(),
                    profileInfo = profileInfoDeferred.await(),
                    regionList = regionListDeferred.await(),
                    selectedImageLevel = _state.value.selectedImageLevel
                )

                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        profileInfo = processedData.profileInfo,
                        profileImages = processedData.profileImages,
                        regionList = processedData.regionList,
                        nickname = processedData.profileInfo.userName,
                        introduction = processedData.profileInfo.introduction,
                        selectedImageLevel = processedData.profileInfo.imageLevel,
                        selectedYear = processedData.selectedYear,
                        selectedMonth = processedData.selectedMonth,
                        selectedDay = processedData.selectedDay,
                        isBirthSelected = processedData.isBirthSelected,
                        selectedRegion = processedData.profileInfo.regionName,
                        selectedRegionId = processedData.profileInfo.regionId,
                        isRegionSelected = processedData.isRegionSelected
                    )
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false) }
                _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
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
            updateSaveButtonState()
            return
        }

        viewModelScope.launch {
            checkNicknameDuplicationUseCase(
                nickname = currentNickname,
                originalNickname = _state.value.profileInfo?.userName
            )
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
            val birthDate = formatBirthDateUseCase(
                isBirthSelected = currentState.isBirthSelected,
                year = currentState.selectedYear,
                month = currentState.selectedMonth,
                day = currentState.selectedDay
            )

            val editModel = currentState.profileInfo?.copy(
                userName = currentState.nickname,
                introduction = currentState.introduction,
                birth = birthDate,
                regionId = if (currentState.isRegionSelected) currentState.selectedRegionId else null,
                imageLevel = currentState.selectedImageLevel
            ) ?: return@launch

            updateProfileInfoUseCase(editModel.toEntity())
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
