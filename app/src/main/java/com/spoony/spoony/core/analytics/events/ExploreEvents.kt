package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONObject

class ExploreEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun sortSelected(sortType: String) {
        tracker.track(
            eventName = "sort_selected",
            properties = JSONObject().apply {
                put("sort_type", sortType)
            }
        )
    }

    fun exploreSearched(
        searchTargetType: String,
        searchTerm: String
    ) {
        tracker.track(
            eventName = "explore_searched",
            properties = JSONObject().apply {
                put("search_target_type", searchTargetType)
                put("search_term", searchTerm)
            }
        )
    }

    fun exploreFilterApplied(
        categoryFilters: List<String>,
        regionFilters: List<String>,
        ageGroupFilters: List<String>
    ) {
        tracker.track(
            eventName = "explore_filter_applied",
            properties = JSONObject().apply {
                put("category_filters", categoryFilters)
                put("region_filters", regionFilters)
                put("age_group_filters", ageGroupFilters)
            }
        )
    }
}
