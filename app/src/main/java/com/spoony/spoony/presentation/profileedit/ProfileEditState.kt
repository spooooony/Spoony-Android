package com.spoony.spoony.presentation.profileedit

import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.presentation.profileedit.model.ProfileEditModel

data class ProfileEditState(
    val isLoading: Boolean = false,
    val profileEditModel: ProfileEditModel = ProfileEditModel.EMPTY,
    val nickname: String = "",
    val nicknameState: NicknameTextFieldState = NicknameTextFieldState.DEFAULT,
    val introduction: String? = null,
    val selectedImageLevel: Int = 1,
    val selectedYear: String? = null,
    val selectedMonth: String? = null,
    val selectedDay: String? = null,
    val isBirthSelected: Boolean = false,
    val selectedRegion: String? = null,
    val selectedRegionId: Int? = null,
    val isRegionSelected: Boolean = false,
    val saveButtonEnabled: Boolean = true
)
