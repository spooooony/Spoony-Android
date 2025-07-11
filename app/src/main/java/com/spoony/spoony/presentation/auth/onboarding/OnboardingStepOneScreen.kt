package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.spoony.spoony.core.designsystem.component.textfield.NicknameTextFieldState
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyNicknameTextField
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.auth.onboarding.component.OnBoardingButton
import com.spoony.spoony.presentation.auth.onboarding.component.OnboardingContent

@Composable
fun OnBoardingStepOneRoute(
    viewModel: OnboardingViewModel,
    onNextButtonClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showSnackbar = LocalSnackBarTrigger.current

    LaunchedEffect(Unit) {
        viewModel.updateCurrentStep(OnboardingSteps.ONE)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is OnboardingSideEffect.ShowSnackbar -> {
                        showSnackbar(sideEffect.message)
                    }
                }
            }
    }

    OnboardingStepOneScreen(
        nickname = state.nickname,
        nicknameState = state.nicknameState,
        onNicknameChanged = viewModel::updateNickname,
        onStateChanged = viewModel::updateNicknameState,
        checkNicknameValid = viewModel::checkUserNameExist,
        onButtonClick = onNextButtonClick
    )
}

@Composable
private fun OnboardingStepOneScreen(
    nickname: String,
    nicknameState: NicknameTextFieldState,
    onNicknameChanged: (String) -> Unit,
    onStateChanged: (NicknameTextFieldState) -> Unit,
    checkNicknameValid: () -> Unit,
    onButtonClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(SpoonyAndroidTheme.colors.white)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 32.dp, bottom = 20.dp)
            .addFocusCleaner(focusManager)
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
                onDoneAction = checkNicknameValid
            )
        }

        OnBoardingButton(
            onClick = onButtonClick,
            enabled = nicknameState == NicknameTextFieldState.AVAILABLE
        )
    }
}
