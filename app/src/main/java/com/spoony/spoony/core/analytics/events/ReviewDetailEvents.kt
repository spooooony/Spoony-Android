package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONObject

class ReviewDetailEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun spoonUseIntent(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "spoon_use_intent",
            properties = JSONObject().apply {
                put("review_id", reviewId)
                put("author_user_id", authorUserId)
                put("place_name", placeName)
                put("category", category)
                put("menu_count", menuCount)
                put("satisfaction_score", satisfactionScore)
                put("review_length", reviewLength)
                put("photo_count", photoCount)
                put("has_disappointment", hasDisappointment)
                put("saved_count", savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun spoonUsed(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "spoon_used",
            properties = JSONObject().apply {
                put("review_id", reviewId)
                put("author_user_id", authorUserId)
                put("place_name", placeName)
                put("category", category)
                put("menu_count", menuCount)
                put("satisfaction_score", satisfactionScore)
                put("review_length", reviewLength)
                put("photo_count", photoCount)
                put("has_disappointment", hasDisappointment)
                put("saved_count", savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun spoonUseFailed() {
        tracker.track("spoon_use_failed")
    }

    fun placeMapSaved(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "place_map_saved",
            properties = JSONObject().apply {
                put("review_id", reviewId)
                put("author_user_id", authorUserId)
                put("place_name", placeName)
                put("category", category)
                put("menu_count", menuCount)
                put("satisfaction_score", satisfactionScore)
                put("review_length", reviewLength)
                put("photo_count", photoCount)
                put("has_disappointment", hasDisappointment)
                put("saved_count", savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun placeMapRemoved(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "place_map_removed",
            properties = JSONObject().apply {
                put("review_id", reviewId)
                put("author_user_id", authorUserId)
                put("place_name", placeName)
                put("category", category)
                put("menu_count", menuCount)
                put("satisfaction_score", satisfactionScore)
                put("review_length", reviewLength)
                put("photo_count", photoCount)
                put("has_disappointment", hasDisappointment)
                put("saved_count", savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun directionClicked(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "direction_clicked",
            properties = JSONObject().apply {
                put("review_id", reviewId)
                put("author_user_id", authorUserId)
                put("place_name", placeName)
                put("category", category)
                put("menu_count", menuCount)
                put("satisfaction_score", satisfactionScore)
                put("review_length", reviewLength)
                put("photo_count", photoCount)
                put("has_disappointment", hasDisappointment)
                put("saved_count", savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }
}
