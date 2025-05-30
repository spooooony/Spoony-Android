package com.spoony.spoony.presentation.profileedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.formatBirthDate
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.UserRepository
import com.spoony.spoony.domain.usecase.CheckNicknameDuplicationUseCase
import com.spoony.spoony.presentation.profileedit.model.ProfileEditModel
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
    private val userRepository: UserRepository,
    private val checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase
) : ViewModel() {

    private val _profileEditModel = MutableStateFlow(ProfileEditModel.EMPTY)
    val profileEditModel: StateFlow<ProfileEditModel>
        get() = _profileEditModel.asStateFlow()

    private val _nicknameState = MutableStateFlow(NicknameTextFieldState.DEFAULT)
    val nicknameState: StateFlow<NicknameTextFieldState>
        get() = _nicknameState.asStateFlow()

    private val _saveButtonEnabled = MutableStateFlow(true)
    val saveButtonEnabled: StateFlow<Boolean>
        get() = _saveButtonEnabled.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileEditSideEffect>()
    val sideEffect: SharedFlow<ProfileEditSideEffect>
        get() = _sideEffect.asSharedFlow()

    init {
        loadProfileEditData()
    }

    private fun loadProfileEditData() {
        viewModelScope.launch {
            runCatching {
                val profileImageDeferred = async { userRepository.getMyProfileImage().getOrThrow() }
                val profileInfoDeferred = async { userRepository.getMyProfileInfo().getOrThrow() }
                val regionListDeferred = async { userRepository.getRegionList().getOrThrow() }

                val profileImageEntity = profileImageDeferred.await()
                val profileInfoEntity = profileInfoDeferred.await()
                val regionEntities = regionListDeferred.await()

                val regionModels = regionEntities.map { it.toModel() }

                profileInfoEntity.toModel(
                    profileImageEntity = profileImageEntity,
                    regionList = regionModels
                )
            }.onSuccess { profileEditModel ->
                _profileEditModel.value = profileEditModel
            }.onFailure {
                _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                _sideEffect.emit(ProfileEditSideEffect.NavigateBack)
            }
        }
    }

    fun updateNickname(nickname: String) {
        _profileEditModel.update { it.copy(userName = nickname) }
        updateSaveButtonState()
    }

    fun updateNicknameState(state: NicknameTextFieldState) {
        _nicknameState.update { state }
        if (state == NicknameTextFieldState.DUPLICATE) {
            _saveButtonEnabled.value = false
        } else {
            updateSaveButtonState()
        }
    }

    fun checkNicknameDuplication() {
        val currentNickname = _profileEditModel.value.userName
        val originalNickname = _profileEditModel.value.originalUserName

        if (currentNickname.isBlank()) {
            _nicknameState.value = NicknameTextFieldState.NICKNAME_REQUIRED
            return
        }

        if (currentNickname == originalNickname) {
            _nicknameState.value = NicknameTextFieldState.AVAILABLE
            updateSaveButtonState()
            return
        }

        viewModelScope.launch {
            checkNicknameDuplicationUseCase(
                nickname = currentNickname,
                originalNickname = originalNickname
            )
                .onSuccess { isDuplicated ->
                    if (isDuplicated) {
                        _nicknameState.value = NicknameTextFieldState.DUPLICATE
                        _saveButtonEnabled.value = false
                    } else {
                        _nicknameState.value = NicknameTextFieldState.AVAILABLE
                    }
                    updateSaveButtonState()
                }
                .onLogFailure {
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    fun updateIntroduction(introduction: String) {
        _profileEditModel.update {
            it.copy(introduction = introduction.takeIf { it.isNotBlank() })
        }
    }

    fun selectImageLevel(level: Int) {
        _profileEditModel.update { currentModel ->
            val updatedImages = currentModel.profileImages.map { image ->
                image.copy(isSelected = image.imageLevel == level)
            }.toImmutableList()

            currentModel.copy(
                imageLevel = level,
                profileImages = updatedImages
            )
        }
    }

    fun selectDate(year: String, month: String, day: String) {
        _profileEditModel.update {
            it.copy(
                selectedYear = year,
                selectedMonth = month,
                selectedDay = day,
                isBirthSelected = true
            )
        }
    }

    fun selectRegion(regionId: Int, regionName: String) {
        _profileEditModel.update {
            it.copy(
                regionId = regionId,
                regionName = "서울 $regionName",
                isRegionSelected = true
            )
        }
    }

    fun updateProfileInfo() {
        _saveButtonEnabled.value = false
        viewModelScope.launch {
            val currentModel = _profileEditModel.value

            val birthDate = formatBirthDate(
                isBirthSelected = currentModel.isBirthSelected,
                year = currentModel.selectedYear,
                month = currentModel.selectedMonth,
                day = currentModel.selectedDay
            )

            val updatedModel = currentModel.copy(birth = birthDate)

            userRepository.updateMyProfileInfo(updatedModel.toEntity())
                .onSuccess {
                    _sideEffect.emit(ProfileEditSideEffect.ShowSnackBar("프로필이 저장되었습니다."))
                    _sideEffect.emit(ProfileEditSideEffect.NavigateBack)
                }
                .onLogFailure {
                    _saveButtonEnabled.value = true
                    _sideEffect.emit(ProfileEditSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun updateSaveButtonState() {
        val isNicknameValid = when (_nicknameState.value) {
            NicknameTextFieldState.DEFAULT -> true
            NicknameTextFieldState.AVAILABLE -> true
            NicknameTextFieldState.DUPLICATE -> false
            else -> false
        }
        _saveButtonEnabled.update { isNicknameValid }
    }
}

sealed class ProfileEditSideEffect {
    data class ShowSnackBar(val message: String) : ProfileEditSideEffect()
    data class ShowError(val errorType: ErrorType) : ProfileEditSideEffect()
    object NavigateBack : ProfileEditSideEffect()
}
