package com.spoony.spoony.presentation.exploreSearch.model

import androidx.compose.ui.graphics.Color
import com.spoony.spoony.core.designsystem.model.ReviewCardCategory
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.core.util.extension.toRelativeTimeOrDate
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.domain.entity.PlaceReviewEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ExploreSearchPlaceReviewModel(
    val reviewId: Int,
    val userId: Int,
    val userName: String,
    val userRegion: String,
    val description: String,
    val photoUrlList: ImmutableList<String> = persistentListOf(),
    val category: ReviewCardCategory,
    val isMine: Boolean,
    val addMapCount: Int,
    val createdAt: String
)

fun PlaceReviewEntity.toModel(): ExploreSearchPlaceReviewModel = ExploreSearchPlaceReviewModel(
    reviewId = this.reviewId ?: 0,
    userId = this.userId ?: 0,
    userName = this.userName ?: "",
    userRegion = this.userRegion ?: "",
    description = this.description,
    photoUrlList = this.photoUrlList?.toImmutableList() ?: persistentListOf(),
    category = this.category?.toModel() ?: ReviewCardCategory(
        text = "",
        iconUrl = "",
        textColor = Color.Unspecified,
        backgroundColor = Color.Unspecified
    ),
    isMine = this.isMine ?: false,
    addMapCount = this.addMapCount ?: 0,
    createdAt = this.createdAt?.toRelativeTimeOrDate() ?: ""
)

fun CategoryEntity.toModel(): ReviewCardCategory = ReviewCardCategory(
    text = this.categoryName,
    iconUrl = this.iconUrl,
    textColor = Color.hexToColor(this.textColor ?: ""),
    backgroundColor = Color.hexToColor(this.backgroundColor ?: "")
)
