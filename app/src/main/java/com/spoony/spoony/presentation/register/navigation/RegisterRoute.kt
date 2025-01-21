package com.spoony.spoony.presentation.register.navigation

import com.spoony.spoony.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed class RegisterRoute : Route {
    @Serializable
    data object StepOne : RegisterRoute()

    @Serializable
    data object StepTwo : RegisterRoute()
}
