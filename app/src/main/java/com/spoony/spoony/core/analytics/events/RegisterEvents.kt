package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class RegisterEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun review1Completed(
        placeName: String,
        category: String,
        menuCount: Int
    ) {
        tracker.track(
            eventName = "review_1_completed",
            properties = """
                {
                    "place_name": "$placeName",
                    "category": "$category",
                    "menu_count": $menuCount
                }
            """.trimIndent()
        )
    }

    fun review2Completed(
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean
    ) {
        tracker.track(
            eventName = "review_2_completed",
            properties = """
                {
                    "review_length": $reviewLength,
                    "photo_count": $photoCount,
                    "has_disappointment": $hasDisappointment
                }
            """.trimIndent()
        )
    }
}
