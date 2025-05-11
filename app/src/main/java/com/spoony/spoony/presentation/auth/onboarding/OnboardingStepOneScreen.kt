package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    OnboardingStepOneScreen(
        nickname = state.nickname,
        onNicknameChanged = viewModel::updateNickname,
        checkNicknameValid = { true },
        onButtonClick = onNextButtonClick
    )
}

@Composable
private fun OnboardingStepOneScreen(
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    checkNicknameValid: (String) -> Boolean,
    onButtonClick: () -> Unit
) {
    var state by remember { mutableStateOf(NicknameTextFieldState.DEFAULT) }

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
                state = state,
                onStateChanged = { state = it },
                minLength = 1,
                maxLength = 10,
                onDoneAction = {
                    state = if (checkNicknameValid(nickname)) {
                        NicknameTextFieldState.AVAILABLE
                    } else {
                        NicknameTextFieldState.DUPLICATE
                    }
                }
            )
        }

        OnBoardingButton(
            onClick = onButtonClick,
            enabled = state == NicknameTextFieldState.AVAILABLE
        )
    }
}
