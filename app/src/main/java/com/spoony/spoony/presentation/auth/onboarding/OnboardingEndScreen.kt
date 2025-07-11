package com.spoony.spoony.presentation.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun OnboardingEndRoute(
    viewModel: OnboardingViewModel,
    navigateToMap: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.updateCurrentStep(OnboardingSteps.END)
    }

    OnboardingEndScreen(
        nickname = state.nickname,
        onButtonClick = navigateToMap
    )
}

@Composable
private fun OnboardingEndScreen(
    nickname: String,
    onButtonClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SpoonyAndroidTheme.colors.black)) {
                    append("반가워요, ")
                }
                withStyle(style = SpanStyle(color = SpoonyAndroidTheme.colors.main400)) {
                    append(nickname)
                }
                withStyle(style = SpanStyle(color = SpoonyAndroidTheme.colors.black)) {
                    append(" 님!")
                }
            },
            style = SpoonyAndroidTheme.typography.title2,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "이제 스푸니를 시작해 볼까요?",
            style = SpoonyAndroidTheme.typography.title2,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        LottieAnimation(
            composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoony_onboarding)).value,
            iterations = LottieConstants.IterateForever
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "내 프로필 정보는 마이페이지에서 변경할 수 있어요",
            style = SpoonyAndroidTheme.typography.caption1m,
            color = SpoonyAndroidTheme.colors.gray400
        )

        Spacer(modifier = Modifier.height(16.dp))

        SpoonyButton(
            text = "스푸니 시작하기",
            size = ButtonSize.Xlarge,
            style = ButtonStyle.Primary,
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
