package com.spoony.spoony.presentation.profileedit.model

data class ProfileImageModel(
    val imageLevel: Int,
    val imageUrl: String,
    val isSelected: Boolean,
    val isUnLocked: Boolean,
    val name: String = "",
    val description: String = ""
)
