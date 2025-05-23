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
import com.spoony.spoony.core.designsystem.component.button.RegionSelectButton
import com.spoony.spoony.core.designsystem.model.BirthDate
import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.toBirthDate
import com.spoony.spoony.presentation.auth.onboarding.component.OnBoardingButton
import com.spoony.spoony.presentation.auth.onboarding.component.OnboardingContent
import kotlinx.collections.immutable.persistentListOf

@Composable
fun OnboardingStepTwoRoute(
    viewModel: OnboardingViewModel,
    onNextButtonClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isButtonEnabled by remember { mutableStateOf(false) }
    var birthBottomSheetVisibility by remember { mutableStateOf(false) }
    var regionBottomSheetVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.updateCurrentStep(OnboardingSteps.TWO)
    }

    LaunchedEffect(state.birth, state.region) {
        isButtonEnabled = state.birth != "" || state.region.regionId != -1
    }

    OnboardingStepTwoScreen(
        birth = state.birth.toBirthDate(),
        region = state.region,
        isButtonEnabled = isButtonEnabled,
        onBirthButtonClick = {
            birthBottomSheetVisibility = true
        },
        onRegionButtonClick = {
            regionBottomSheetVisibility = true
            viewModel.getRegionList()
        },
        onNextButtonClick = onNextButtonClick
    )

    if (birthBottomSheetVisibility) {
        with(state.birth.toBirthDate()) {
            SpoonyDatePickerBottomSheet(
                onDismiss = { birthBottomSheetVisibility = false },
                onDateSelected = viewModel::updateBirth,
                initialYear = this?.year?.toIntOrNull() ?: 2000,
                initialMonth = this?.month?.toIntOrNull() ?: 1,
                initialDay = this?.day?.toIntOrNull() ?: 1
            )
        }
    }

    if (regionBottomSheetVisibility) {
        SpoonyRegionBottomSheet(
            regionList = (state.regionList as? UiState.Success)?.data ?: persistentListOf(),
            onDismiss = { regionBottomSheetVisibility = false },
            onClick = viewModel::updateRegion
        )
    }
}

@Composable
private fun OnboardingStepTwoScreen(
    birth: BirthDate?,
    region: RegionModel,
    isButtonEnabled: Boolean,
    onBirthButtonClick: () -> Unit,
    onRegionButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
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
                onClick = onBirthButtonClick,
                year = birth?.year ?: "2000",
                month = birth?.month ?: "01",
                day = birth?.day ?: "01",
                isBirthSelected = birth != null
            )
        }

        OnboardingContent("주로 활동하는 지역을 설정해 주세요") {
            RegionSelectButton(
                onClick = onRegionButtonClick,
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
}
