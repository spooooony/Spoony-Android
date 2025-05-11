package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyNicknameTextField
import com.spoony.spoony.presentation.auth.onboarding.component.OnBoardingButton
import com.spoony.spoony.presentation.auth.onboarding.component.OnboardingContent

@Composable
fun OnBoardingStepOneRoute(
    viewModel: OnboardingViewModel,
    onNextButtonClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.updateCurrentStep(OnboardingSteps.ONE)
    }

    OnboardingStepOneScreen(
        nickname = state.nickname,
        nicknameState = state.nicknameState,
        onNicknameChanged = viewModel::updateNickname,
        onStateChanged = viewModel::updateNicknameState,
        checkNicknameValid = { true },
        onButtonClick = onNextButtonClick
    )
}

@Composable
private fun OnboardingStepOneScreen(
    nickname: String,
    nicknameState: NicknameTextFieldState,
    onNicknameChanged: (String) -> Unit,
    onStateChanged: (NicknameTextFieldState) -> Unit,
    checkNicknameValid: (String) -> Boolean,
    onButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 32.dp, bottom = 20.dp)
    ) {
        OnboardingContent("닉네임을 입력해주세요") {
            SpoonyNicknameTextField(
                value = nickname,
                placeholder = "스푼의 이름을 정해주세요 (한글,영문,숫자 입력 가능)",
                onValueChanged = onNicknameChanged,
                state = nicknameState,
                onStateChanged = onStateChanged,
                minLength = 1,
                maxLength = 10,
                onDoneAction = {
                    onStateChanged(
                        if (checkNicknameValid(nickname)) {
                            NicknameTextFieldState.AVAILABLE
                        } else {
                            NicknameTextFieldState.DUPLICATE
                        }
                    )
                }
            )
        }

        OnBoardingButton(
            onClick = onButtonClick,
            enabled = nicknameState == NicknameTextFieldState.AVAILABLE
        )
    }
}
