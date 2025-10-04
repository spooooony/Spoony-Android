package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class ExploreEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun sortSelected(sortType: String) {
        tracker.track(
            eventName = "sort_selected",
            properties = """
                {
                    "sort_type": "$sortType"
                }
            """.trimIndent()
        )
    }

    fun exploreSearched(
        searchTargetType: String,
        searchTerm: String
    ) {
        tracker.track(
            eventName = "explore_searched",
            properties = """
                {
                    "search_target_type": "$searchTargetType",
                    "search_term": "$searchTerm"
                }
            """.trimIndent()
        )
    }
}
