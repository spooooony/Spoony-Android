package com.spoony.spoony.presentation.auth.onboarding

import com.spoony.spoony.core.designsystem.model.RegionModel

enum class OnboardingSteps(val step: Float) {
    ONE(1f),
    TWO(2f),
    THREE(3f),
    FINAL(0f)
}

data class OnboardingState(
    val nickname: String = "",
    val birth: String = "",
    val region: RegionModel = RegionModel(-1, ""),
    val introduction: String = "",
    val currentStep: Float = 1f,
)
