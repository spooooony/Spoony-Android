package com.spoony.spoony.core.analytics.events

import androidx.compose.runtime.staticCompositionLocalOf
import jakarta.inject.Inject

val LocalTracker = staticCompositionLocalOf<MixPanelEvents> {
    error("No MixPanelEvents provided")
}

class MixPanelEvents @Inject constructor(
    val userProperties: MixPanelUserProperties,
    val analyticsEvents: AnalyticsEvents,
    val commonEvents: CommonEvents,
    val onboardingEvents: OnboardingEvents,
    val spoonDrawEvents: SpoonDrawEvents,
    val mapEvents: MapEvents,
    val exploreEvents: ExploreEvents,
    val registerEvents: RegisterEvents
)
