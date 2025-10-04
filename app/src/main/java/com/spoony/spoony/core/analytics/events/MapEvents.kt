package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class MapEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun mapSearched(
        locationType: String,
        searchTerm: String
    ) {
        tracker.track(
            eventName = "map_searched",
            properties = """
                {
                    "location_type": "$locationType",
                    "search_term": "$searchTerm"
                }
            """.trimIndent()
        )
    }
}
