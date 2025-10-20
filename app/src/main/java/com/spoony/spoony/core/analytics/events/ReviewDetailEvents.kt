package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import com.spoony.spoony.core.analytics.model.ReviewTrackingModel
import jakarta.inject.Inject
import org.json.JSONObject

class ReviewDetailEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun spoonUseIntent(
        reviewTrackingModel: ReviewTrackingModel,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "spoon_use_intent",
            properties = JSONObject().apply {
                put("review_id", reviewTrackingModel.reviewId)
                put("author_user_id", reviewTrackingModel.authorUserId)
                put("place_name", reviewTrackingModel.placeName)
                put("category", reviewTrackingModel.category)
                put("menu_count", reviewTrackingModel.menuCount)
                put("satisfaction_score", reviewTrackingModel.satisfactionScore)
                put("review_length", reviewTrackingModel.reviewLength)
                put("photo_count", reviewTrackingModel.photoCount)
                put("has_disappointment", reviewTrackingModel.hasDisappointment)
                put("saved_count", reviewTrackingModel.savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun spoonUsed(
        reviewTrackingModel: ReviewTrackingModel,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "spoon_used",
            properties = JSONObject().apply {
                put("review_id", reviewTrackingModel.reviewId)
                put("author_user_id", reviewTrackingModel.authorUserId)
                put("place_name", reviewTrackingModel.placeName)
                put("category", reviewTrackingModel.category)
                put("menu_count", reviewTrackingModel.menuCount)
                put("satisfaction_score", reviewTrackingModel.satisfactionScore)
                put("review_length", reviewTrackingModel.reviewLength)
                put("photo_count", reviewTrackingModel.photoCount)
                put("has_disappointment", reviewTrackingModel.hasDisappointment)
                put("saved_count", reviewTrackingModel.savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun spoonUseFailed() {
        tracker.track("spoon_use_failed")
    }

    fun placeMapSaved(
        reviewTrackingModel: ReviewTrackingModel,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "place_map_saved",
            properties = JSONObject().apply {
                put("review_id", reviewTrackingModel.reviewId)
                put("author_user_id", reviewTrackingModel.authorUserId)
                put("place_name", reviewTrackingModel.placeName)
                put("category", reviewTrackingModel.category)
                put("menu_count", reviewTrackingModel.menuCount)
                put("satisfaction_score", reviewTrackingModel.satisfactionScore)
                put("review_length", reviewTrackingModel.reviewLength)
                put("photo_count", reviewTrackingModel.photoCount)
                put("has_disappointment", reviewTrackingModel.hasDisappointment)
                put("saved_count", reviewTrackingModel.savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun placeMapRemoved(
        reviewTrackingModel: ReviewTrackingModel,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "place_map_removed",
            properties = JSONObject().apply {
                put("review_id", reviewTrackingModel.reviewId)
                put("author_user_id", reviewTrackingModel.authorUserId)
                put("place_name", reviewTrackingModel.placeName)
                put("category", reviewTrackingModel.category)
                put("menu_count", reviewTrackingModel.menuCount)
                put("satisfaction_score", reviewTrackingModel.satisfactionScore)
                put("review_length", reviewTrackingModel.reviewLength)
                put("photo_count", reviewTrackingModel.photoCount)
                put("has_disappointment", reviewTrackingModel.hasDisappointment)
                put("saved_count", reviewTrackingModel.savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }

    fun directionClicked(
        reviewTrackingModel: ReviewTrackingModel,
        isFollowingAuthor: Boolean
    ) {
        tracker.track(
            eventName = "direction_clicked",
            properties = JSONObject().apply {
                put("review_id", reviewTrackingModel.reviewId)
                put("author_user_id", reviewTrackingModel.authorUserId)
                put("place_name", reviewTrackingModel.placeName)
                put("category", reviewTrackingModel.category)
                put("menu_count", reviewTrackingModel.menuCount)
                put("satisfaction_score", reviewTrackingModel.satisfactionScore)
                put("review_length", reviewTrackingModel.reviewLength)
                put("photo_count", reviewTrackingModel.photoCount)
                put("has_disappointment", reviewTrackingModel.hasDisappointment)
                put("saved_count", reviewTrackingModel.savedCount)
                put("is_following_author", isFollowingAuthor)
            }
        )
    }
}
