package com.spoony.spoony.presentation.auth.onboarding

import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.state.UiState
import kotlinx.collections.immutable.ImmutableList

enum class OnboardingSteps() {
    END,
    ONE,
    TWO,
    THREE;

    val step: Float
        get() = this.ordinal.toFloat()
}

data class OnboardingState(
    val nickname: String = "",
    val nicknameState: NicknameTextFieldState = NicknameTextFieldState.DEFAULT,
    val birth: String = "",
    val region: RegionModel = RegionModel(-1, "마포구"),
    val introduction: String = "",
    val currentStep: OnboardingSteps = OnboardingSteps.ONE,
    val regionList: UiState<ImmutableList<RegionModel>> = UiState.Loading
)
