package com.spoony.spoony.presentation.profileedit.model

import com.spoony.spoony.domain.entity.ProfileImageEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ProfileImageModel(
    val imageLevel: Int,
    val imageUrl: String,
    val spoonName: String,
    val isSelected: Boolean,
    val isUnLocked: Boolean,
    val description: String = ""
)

fun ProfileImageEntity.toModel(selectedLevel: Int = 1): ImmutableList<ProfileImageModel> =
    this.images.map { image ->
        ProfileImageModel(
            imageLevel = image.imageLevel,
            imageUrl = image.imageUrl,
            spoonName = image.spoonName,
            isSelected = image.imageLevel == selectedLevel,
            isUnLocked = image.isUnlocked,
            description = image.unlockCondition
        )
    }.toImmutableList()
