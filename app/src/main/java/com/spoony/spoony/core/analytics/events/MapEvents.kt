package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONObject

class MapEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun mapSearched(
        locationType: String,
        searchTerm: String
    ) {
        tracker.track(
            eventName = "map_searched",
            properties = JSONObject().apply {
                put("location_type", locationType)
                put("search_term", searchTerm)
            }
        )
    }
}
