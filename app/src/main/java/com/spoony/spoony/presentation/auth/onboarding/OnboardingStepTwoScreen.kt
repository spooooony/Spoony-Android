package com.spoony.spoony.presentation.auth.onboarding

import BirthSelectButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyDatePickerBottomSheet
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyRegionBottomSheet
import com.spoony.spoony.core.designsystem.component.bottomsheet.regionList
import com.spoony.spoony.core.designsystem.component.button.RegionSelectButton
import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.auth.onboarding.component.OnBoardingButton
import com.spoony.spoony.presentation.auth.onboarding.component.OnboardingContent

@Composable
fun OnboardingStepTwoRoute(
    viewModel: OnboardingViewModel,
    onNextButtonClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.updateCurrentStep(OnboardingSteps.TWO)
    }

    LaunchedEffect(state.birth, state.region) {
        isButtonEnabled = state.birth != "" || state.region.regionId != -1
    }

    OnboardingStepTwoScreen(
        birth = state.birth.split("-"),
        region = state.region,
        isButtonEnabled = isButtonEnabled,
        onUpdateBirth = viewModel::updateBirth,
        onUpdateRegion = viewModel::updateRegion,
        onNextButtonClick = onNextButtonClick
    )
}

@Composable
private fun OnboardingStepTwoScreen(
    birth: List<String>,
    region: RegionModel,
    isButtonEnabled: Boolean,
    onUpdateBirth: (String) -> Unit,
    onUpdateRegion: (RegionModel) -> Unit,
    onNextButtonClick: () -> Unit
) {
    var birthBottomSheetVisibility by remember { mutableStateOf(false) }
    var regionBottomSheetVisibility by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(55.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(horizontal = 20.dp)
            .padding(top = 32.dp, bottom = 20.dp)
    ) {
        OnboardingContent("생년월일을 입력해주세요") {
            BirthSelectButton(
                onClick = {
                    birthBottomSheetVisibility = true
                },
                year = birth.getOrNull(0)?.takeIf { it.isNotBlank() } ?: "2000",
                month = birth.getOrNull(1) ?: "01",
                day = birth.getOrNull(2) ?: "01",
                isBirthSelected = birth.getOrNull(0) != ""
            )
        }

        OnboardingContent("주로 활동하는 지역을 설정해 주세요") {
            RegionSelectButton(
                onClick = {
                    regionBottomSheetVisibility = true
                },
                region = "서울 ${region.regionName}",
                isSelected = region.regionId != -1
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        OnBoardingButton(
            onClick = onNextButtonClick,
            enabled = isButtonEnabled
        )
    }

    if (birthBottomSheetVisibility) {
        SpoonyDatePickerBottomSheet(
            onDismiss = { birthBottomSheetVisibility = false },
            onDateSelected = onUpdateBirth,
            initialYear = birth.getOrNull(0)?.toIntOrNull() ?: 2000,
            initialMonth = birth.getOrNull(1)?.toIntOrNull() ?: 1,
            initialDay = birth.getOrNull(2)?.toIntOrNull() ?: 1
        )
    }

    if (regionBottomSheetVisibility) {
        SpoonyRegionBottomSheet(
            regionList = regionList,
            onDismiss = { regionBottomSheetVisibility = false },
            onClick = onUpdateRegion
        )
    }
}
