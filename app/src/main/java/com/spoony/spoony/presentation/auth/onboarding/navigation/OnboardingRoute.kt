package com.spoony.spoony.presentation.auth.onboarding.navigation

import com.spoony.spoony.core.navigation.Route
import kotlinx.serialization.Serializable

sealed class OnboardingRoute : Route {
    @Serializable
    data object StepOne : OnboardingRoute()

    @Serializable
    data object StepTwo : OnboardingRoute()

    @Serializable
    data object StepThree : OnboardingRoute()

    @Serializable
    data object End : OnboardingRoute()
}
