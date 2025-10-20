package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONObject

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
            properties = JSONObject().apply {
                put("place_name", placeName)
                put("category", category)
                put("menu_count", menuCount)
            }
        )
    }

    fun review2Completed(
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean
    ) {
        tracker.track(
            eventName = "review_2_completed",
            properties = JSONObject().apply {
                put("review_length", reviewLength)
                put("photo_count", photoCount)
                put("has_disappointment", hasDisappointment)
            }
        )
    }
}
