package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.auth.onboarding.component.OnBoardingButton
import com.spoony.spoony.presentation.auth.onboarding.component.OnboardingContent

@Composable
fun OnboardingStepThreeRoute(
    viewModel: OnboardingViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.updateCurrentStep(OnboardingSteps.THREE)
    }

    OnboardingStepThreeScreen(
        introduction = state.introduction,
        onValueChanged = viewModel::updateIntroduction,
        onButtonClick = viewModel::signUp
    )
}

@Composable
private fun OnboardingStepThreeScreen(
    introduction: String?,
    onValueChanged: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(SpoonyAndroidTheme.colors.white)
            .padding(horizontal = 20.dp)
            .padding(top = 32.dp, bottom = 20.dp)
            .addFocusCleaner(focusManager)
    ) {
        OnboardingContent("간단한 자기소개를 입력해 주세요") {
            SpoonyLargeTextField(
                value = introduction.orEmpty(),
                placeholder = "안녕! 나는 어떤 스푼이냐면...",
                onValueChanged = onValueChanged,
                minLength = 1,
                maxLength = 50,
                maxErrorText = "50자 이하로 입력해 주세요",
                isAllowSpecialChars = true
            )
        }

        OnBoardingButton(
            onClick = onButtonClick,
            enabled = introduction?.trim()?.isNotBlank() ?: false
        )
    }
}
