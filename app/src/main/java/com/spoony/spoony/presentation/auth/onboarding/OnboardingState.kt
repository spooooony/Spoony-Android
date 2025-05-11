package com.spoony.spoony.presentation.auth.onboarding

import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.model.RegionModel

enum class OnboardingSteps(val step: Float) {
    ONE(1f),
    TWO(2f),
    THREE(3f),
    END(0f)
}

data class OnboardingState(
    val nickname: String = "",
    val nicknameState: NicknameTextFieldState = NicknameTextFieldState.DEFAULT,
    val birth: String = "",
    val region: RegionModel = RegionModel(-1, "마포구"),
    val introduction: String = "",
    val currentStep: OnboardingSteps = OnboardingSteps.ONE
)
