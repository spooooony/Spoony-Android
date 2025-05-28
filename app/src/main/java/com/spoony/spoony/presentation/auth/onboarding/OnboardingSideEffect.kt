package com.spoony.spoony.presentation.auth.onboarding

sealed class OnboardingSideEffect {
    data class ShowSnackbar(val message: String) : OnboardingSideEffect()
}
