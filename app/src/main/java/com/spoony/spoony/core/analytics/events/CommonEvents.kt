package com.spoony.spoony.core.analytics.events

import com.spoony.spoony.core.analytics.MixPanelTracker
import jakarta.inject.Inject
import org.json.JSONArray

class CommonEvents @Inject constructor(
    private val tracker: MixPanelTracker
) {
    fun tabEntered(tabName: String) {
        tracker.track(
            eventName = "tab_entered",
            properties = """
                {
                    "tab_name": "$tabName"
                }
            """.trimIndent()
        )
    }

    fun reviewViewed(
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
        isSelfReview: Boolean,
        isFollowedUserReview: Boolean,
        isSavedReview: Boolean
//        entryPoint: String
    ) {
        tracker.track(
            eventName = "review_viewed",
            properties = """
                {
                    "review_id": $reviewId,
                    "author_user_id": $authorUserId,
                    "place_name": "$placeName",
                    "menu_count": $menuCount,
                    "satisfaction_score": $satisfactionScore,
                    "review_length": $reviewLength,
                    "photo_count": $photoCount,
                    "has_disappointment": $hasDisappointment,
                    "saved_count": $savedCount,
                    "is_self_review": $isSelfReview,
                    "is_followed_user_review": $isFollowedUserReview,
                    "is_saved_review": $isSavedReview
                }
            """.trimIndent()
        )
    }

    fun reviewEdited(
        reviewId: Int,
//        authorUserId: Int,
        placeName: String,
        category: String,
        menuCount: Int,
        satisfactionScore: Float,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean
//        savedCount: Int,
//        entryPoint: String
    ) {
        tracker.track(
            eventName = "review_edited",
            properties = """
                {
                    "review_id": $reviewId,
                    "place_name": "$placeName",
                    "category": "$category",
                    "menu_count": $menuCount,
                    "satisfaction_score": $satisfactionScore,
                    "review_length": $reviewLength,
                    "photo_count": $photoCount,
                    "has_disappointment": $hasDisappointment
                }
            """.trimIndent()
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
            properties = """
                {
                    "profile_user_id": $profileUserId,
                    "is_self_profile": $isSelfProfile,
                    "is_following_profile_user": $isFollowingProfileUser
                }
            """.trimIndent()
        )
    }

    fun followUser(
        followedUserId: Int,
        entryPoint: String
    ) {
        tracker.track(
            eventName = "follow_user",
            properties = """
                {
                    "followed_user_id": $followedUserId,
                    "entry_point" : "$entryPoint"
                }
            """.trimIndent()
        )
    }

    fun unfollowUser(
        unfollowedUserId: Int,
        entryPoint: String
    ) {
        tracker.track(
            eventName = "unfollow_user",
            properties = """
                {
                    "unfollowed_user_id": $unfollowedUserId,
                    "entry_point": "$entryPoint"
                }
            """.trimIndent()
        )
    }

    fun followUserFromReview(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int
    ) {
        tracker.track(
            eventName = "follow_user_from_review",
            properties = """
                {
                    "review_id": $reviewId,
                    "author_user_id": $authorUserId,
                    "place_name": "$placeName",
                    "menu_count": $menuCount,
                    "satisfaction_score": $satisfactionScore,
                    "review_length": $reviewLength,
                    "photo_count": $photoCount,
                    "has_disappointment": $hasDisappointment,
                    "saved_count": $savedCount,
                    "entry_point" : "review"
                }
            """.trimIndent()
        )
    }

    fun unfollowUserFromReview(
        reviewId: Int,
        authorUserId: Int,
        placeName: String,
//        category: String,
        menuCount: Int,
        satisfactionScore: Double,
        reviewLength: Int,
        photoCount: Int,
        hasDisappointment: Boolean,
        savedCount: Int
    ) {
        tracker.track(
            eventName = "unfollow_user_from_review",
            properties = """
                {
                    "review_id": $reviewId,
                    "author_user_id": $authorUserId,
                    "place_name": "$placeName",
                    "menu_count": $menuCount,
                    "satisfaction_score": $satisfactionScore,
                    "review_length": $reviewLength,
                    "photo_count": $photoCount,
                    "has_disappointment": $hasDisappointment,
                    "saved_count": $savedCount,
                    "entry_point" : "review"
                }
            """.trimIndent()
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
            properties = """
                {
                    "page_applied": "$pageApplied",
                    "local_review_filter": ${localReviewFilter},
                    "region_filters": ${JSONArray(regionFilters)},
                    "category_filters": ${JSONArray(categoryFilters)},
                    "age_group_filters": ${JSONArray(ageGroupFilters)}
                }
            """.trimIndent()
        )
    }
}
