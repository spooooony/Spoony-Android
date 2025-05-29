package com.spoony.spoony.presentation.profileedit.model

import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.util.extension.toBirthDate
import com.spoony.spoony.domain.entity.ProfileImageEntity
import com.spoony.spoony.domain.entity.ProfileInfoEntity
import com.spoony.spoony.domain.entity.ProfileUpdateEntity
import com.spoony.spoony.domain.entity.RegionEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ProfileEditModel(
    val userName: String,
    val originalUserName: String,
    val regionName: String?,
    val regionId: Int?,
    val introduction: String?,
    val birth: String?,
    val imageLevel: Int,
    val profileImages: ImmutableList<ProfileImageModel>,
    val regionList: ImmutableList<RegionModel>,
    val selectedYear: String?,
    val selectedMonth: String?,
    val selectedDay: String?,
    val isBirthSelected: Boolean,
    val isRegionSelected: Boolean
) {
    companion object {
        val EMPTY = ProfileEditModel(
            userName = "",
            originalUserName = "",
            regionName = null,
            regionId = null,
            introduction = null,
            birth = null,
            imageLevel = 1,
            profileImages = persistentListOf(),
            regionList = persistentListOf(),
            selectedYear = null,
            selectedMonth = null,
            selectedDay = null,
            isBirthSelected = false,
            isRegionSelected = false
        )
    }
}

data class ProfileImageModel(
    val imageLevel: Int,
    val imageUrl: String,
    val spoonName: String,
    val isSelected: Boolean,
    val isUnLocked: Boolean,
    val description: String = ""
)

fun ProfileInfoEntity.toModel(
    profileImageEntity: ProfileImageEntity,
    regionList: List<RegionModel>,
    selectedImageLevel: Int = this.imageLevel
): ProfileEditModel {
    val profileImageModels = profileImageEntity.images.map { image ->
        ProfileImageModel(
            imageLevel = image.imageLevel,
            imageUrl = image.imageUrl,
            spoonName = image.spoonName,
            isSelected = image.imageLevel == selectedImageLevel,
            isUnLocked = image.isUnlocked,
            description = image.unlockCondition
        )
    }.toImmutableList()

    val birthDate = this.birth.toBirthDate()
    val fullRegionName = this.regionName.takeIf { it.isNotBlank() }?.let { "서울 $it" }
    val matchedRegionId = fullRegionName?.let { regionName ->
        regionList.find { it.regionName == regionName.removePrefix("서울 ") }?.regionId
    }

    return ProfileEditModel(
        userName = this.userName,
        originalUserName = this.userName,
        regionName = fullRegionName,
        regionId = matchedRegionId,
        introduction = this.introduction,
        birth = this.birth,
        imageLevel = this.imageLevel,
        profileImages = profileImageModels,
        regionList = regionList.toImmutableList(),
        selectedYear = birthDate?.year,
        selectedMonth = birthDate?.month,
        selectedDay = birthDate?.day,
        isBirthSelected = birthDate != null,
        isRegionSelected = fullRegionName != null
    )
}

fun ProfileEditModel.toEntity(): ProfileUpdateEntity = ProfileUpdateEntity(
    userName = this.userName,
    regionId = this.regionId,
    introduction = this.introduction?.takeIf { it.isNotBlank() },
    birth = this.birth?.takeIf { it.isNotBlank() },
    imageLevel = this.imageLevel
)

fun RegionEntity.toModel(): RegionModel = RegionModel(
    regionId = this.regionId,
    regionName = this.regionName
)
