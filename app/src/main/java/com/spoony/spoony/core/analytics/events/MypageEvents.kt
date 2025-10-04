package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONArray

class MypageEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun profileUpdated(fieldsUpdated: List<String> = listOf()) {
        tracker.track(
            eventName = "profile_updated",
            properties = """
                {
                    "fields_updated": ${JSONArray(fieldsUpdated)}
                }
            """.trimIndent()
        )
    }

    fun spoonCharacterViewed() {
        tracker.track("spoon_character_viewed")
    }
}
