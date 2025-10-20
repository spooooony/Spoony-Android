package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import com.spoony.spoony.core.analytics.model.ReviewTrackingModel
import jakarta.inject.Inject
import org.json.JSONArray
import org.json.JSONObject

class CommonEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun tabEntered(tabName: String) {
        tracker.track(
            eventName = "tab_entered",
            properties = JSONObject().apply {
                put("tab_name", tabName)
            }
        )
    }

    fun reviewViewed(
        reviewTrackingModel: ReviewTrackingModel,
        isSelfReview: Boolean,
        isFollowedUserReview: Boolean,
        isSavedReview: Boolean
    ) {
        tracker.track(
            eventName = "review_viewed",
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
                put("is_self_review", isSelfReview)
                put("is_followed_user_review", isFollowedUserReview)
                put("is_saved_review", isSavedReview)
            }
        )
    }

    fun reviewEdited(
        reviewTrackingModel: ReviewTrackingModel
    ) {
        tracker.track(
            eventName = "review_edited",
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
            }
        )
    }

    fun profileViewed(
        profileUserId: Int,
        isSelfProfile: Boolean,
        isFollowingProfileUser: Boolean
//        entryPoint: String
    ) {
        tracker.track(
            eventName = "profile_viewed",
            properties = JSONObject().apply {
                put("profile_user_id", profileUserId)
                put("is_self_profile", isSelfProfile)
                put("is_following_profile_user", isFollowingProfileUser)
            }
        )
    }

    fun followUser(
        followedUserId: Int,
        entryPoint: String
    ) {
        tracker.track(
            eventName = "follow_user",
            properties = JSONObject().apply {
                put("followed_user_id", followedUserId)
                put("entry_point", entryPoint)
            }
        )
    }

    fun unfollowUser(
        unfollowedUserId: Int,
        entryPoint: String
    ) {
        tracker.track(
            eventName = "unfollow_user",
            properties = JSONObject().apply {
                put("unfollowed_user_id", unfollowedUserId)
                put("entry_point", entryPoint)
            }
        )
    }

    fun followUserFromReview(
        reviewTrackingModel: ReviewTrackingModel
    ) {
        tracker.track(
            eventName = "follow_user_from_review",
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
                put("entry_point", "review")
            }
        )
    }

    fun unfollowUserFromReview(
        reviewTrackingModel: ReviewTrackingModel
    ) {
        tracker.track(
            eventName = "unfollow_user_from_review",
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
                put("entry_point", "review")
            }
        )
    }

    fun filterApplied(
        pageApplied: String,
        localReviewFilter: Boolean? = null,
        regionFilters: List<String> = listOf(),
        categoryFilters: List<String> = listOf(),
        ageGroupFilters: List<String> = listOf()
    ) {
        tracker.track(
            eventName = "filter_applied",
            properties = JSONObject().apply {
                put("page_applied", pageApplied)
                put("local_review_filter", localReviewFilter)
                put("region_filters", JSONArray(regionFilters))
                put("category_filters", JSONArray(categoryFilters))
                put("age_group_filters", JSONArray(ageGroupFilters))
            }
        )
    }
}
