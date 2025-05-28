package com.spoony.spoony.presentation.profileedit.model

import com.spoony.spoony.domain.entity.ProfileInfoEntity
import com.spoony.spoony.domain.entity.ProfileUpdateEntity

data class ProfileEditModel(
    val userName: String,
    val regionName: String?,
    val regionId: Int?,
    val introduction: String?,
    val birth: String?,
    val imageLevel: Int
)

fun ProfileInfoEntity.toModel(): ProfileEditModel = ProfileEditModel(
    userName = this.userName,
    regionName = this.regionName.takeIf { it.isNotBlank() }?.let { "서울 $it" },
    regionId = null,
    introduction = this.introduction,
    birth = this.birth,
    imageLevel = this.imageLevel
)

fun ProfileEditModel.toEntity(): ProfileUpdateEntity = ProfileUpdateEntity(
    userName = this.userName,
    regionId = this.regionId,
    introduction = this.introduction?.takeIf { it.isNotBlank() },
    birth = this.birth?.takeIf { it.isNotBlank() },
    imageLevel = this.imageLevel
)
