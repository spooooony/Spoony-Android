package com.spoony.spoony.domain.usecase

import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.util.extension.toBirthDate
import com.spoony.spoony.domain.entity.ProfileImageEntity
import com.spoony.spoony.domain.entity.ProfileInfoEntity
import com.spoony.spoony.domain.entity.RegionEntity
import com.spoony.spoony.presentation.profileedit.model.ProfileEditModel
import com.spoony.spoony.presentation.profileedit.model.ProfileImageModel
import com.spoony.spoony.presentation.profileedit.model.toModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ProcessedProfileEditData(
    val profileInfo: ProfileEditModel,
    val profileImages: ImmutableList<ProfileImageModel>,
    val regionList: ImmutableList<RegionModel>,
    val selectedYear: String?,
    val selectedMonth: String?,
    val selectedDay: String?,
    val isBirthSelected: Boolean,
    val isRegionSelected: Boolean
)

class ProcessProfileEditDataUseCase @Inject constructor(
    private val matchRegionIdUseCase: MatchRegionIdUseCase
) {
    operator fun invoke(
        profileImage: ProfileImageEntity,
        profileInfo: ProfileInfoEntity,
        regionList: List<RegionEntity>,
        selectedImageLevel: Int
    ): ProcessedProfileEditData {
        val profileImageModels = profileImage.toModel(selectedImageLevel)
        val editModel = profileInfo.toModel()
        val birthDate = editModel.birth?.toBirthDate()
        val regionModels = regionList.map { entity ->
            RegionModel(
                regionId = entity.regionId,
                regionName = entity.regionName
            )
        }.toImmutableList()

        val matchedRegionId = editModel.regionName?.let { regionName ->
            matchRegionIdUseCase(regionName, regionModels)
        }

        val finalEditModel = if (matchedRegionId != null) {
            editModel.copy(regionId = matchedRegionId)
        } else {
            editModel
        }

        return ProcessedProfileEditData(
            profileInfo = finalEditModel,
            profileImages = profileImageModels,
            regionList = regionModels,
            selectedYear = birthDate?.year,
            selectedMonth = birthDate?.month,
            selectedDay = birthDate?.day,
            isBirthSelected = birthDate != null,
            isRegionSelected = editModel.regionName != null
        )
    }
}
