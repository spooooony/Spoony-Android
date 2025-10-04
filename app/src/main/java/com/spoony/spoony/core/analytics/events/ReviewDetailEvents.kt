package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject

class ReviewDetailEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun spoonUseIntent(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
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
            properties = """
                {
                    "review_id" : $reviewId,
                    "author_user_id" : $authorUserId,
                    "place_name" : "$placeName",
                    "menu_count" : $menuCount,
                    "satisfaction_score" : $satisfactionScore,
                    "review_length" : $reviewLength,
                    "photo_count" : $photoCount,
                    "has_disappointment" : $hasDisappointment,
                    "saved_count" : $savedCount,
                    "is_following_author" : $isFollowingAuthor
                }
            """.trimIndent()
        )
    }

    fun spoonUsed(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
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
            properties = """
                {
                    "review_id" : $reviewId,
                    "author_user_id" : $authorUserId,
                    "place_name" : "$placeName",
                    "menu_count" : $menuCount,
                    "satisfaction_score" : $satisfactionScore,
                    "review_length" : $reviewLength,
                    "photo_count" : $photoCount,
                    "has_disappointment" : $hasDisappointment,
                    "saved_count" : $savedCount,
                    "is_following_author" : $isFollowingAuthor
                }
            """.trimIndent()
        )
    }

    fun spoonUseFailed() {
        tracker.track("spoon_use_failed")
    }

    fun placeMapSaved(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
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
            properties = """
                {
                    "review_id" : $reviewId,
                    "author_user_id" : $authorUserId,
                    "place_name" : "$placeName",
                    "menu_count" : $menuCount,
                    "satisfaction_score" : $satisfactionScore,
                    "review_length" : $reviewLength,
                    "photo_count" : $photoCount,
                    "has_disappointment" : $hasDisappointment,
                    "saved_count" : $savedCount,
                    "is_following_author" : $isFollowingAuthor
                }
            """.trimIndent()
        )
    }

    fun placeMapRemoved(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
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
            properties = """
                {
                    "review_id" : $reviewId,
                    "author_user_id" : $authorUserId,
                    "place_name" : "$placeName",
                    "menu_count" : $menuCount,
                    "satisfaction_score" : $satisfactionScore,
                    "review_length" : $reviewLength,
                    "photo_count" : $photoCount,
                    "has_disappointment" : $hasDisappointment,
                    "saved_count" : $savedCount,
                    "is_following_author" : $isFollowingAuthor
                }
            """.trimIndent()
        )
    }

    fun directionClicked(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
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
            properties = """
                {
                    "review_id" : $reviewId,
                    "author_user_id" : $authorUserId,
                    "place_name" : "$placeName",
                    "menu_count" : $menuCount,
                    "satisfaction_score" : $satisfactionScore,
                    "review_length" : $reviewLength,
                    "photo_count" : $photoCount,
                    "has_disappointment" : $hasDisappointment,
                    "saved_count" : $savedCount,
                    "is_following_author" : $isFollowingAuthor
                }
            """.trimIndent()
        )
    }
}
