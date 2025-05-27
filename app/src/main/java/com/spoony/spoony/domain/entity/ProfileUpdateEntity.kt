package com.spoony.spoony.domain.entity

data class ProfileUpdateEntity(
    val userName: String,
    val regionId: Int,
    val introduction: String,
    val birth: String,
    val imageLevel: Int
)
