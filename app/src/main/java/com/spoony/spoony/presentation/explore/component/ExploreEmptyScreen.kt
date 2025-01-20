package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
fun ExploreEmptyScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoony_explore_empty))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 56.dp)
    ) {
        LottieAnimation(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .aspectRatio(1f),
            composition = lottieComposition,
            iterations = LottieConstants.IterateForever
        )

        Text(
            text = "아직 발견된 장소가 없어요.\n나만의 리스트를 공유해 볼까요?",
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    top = 30.dp,
                    bottom = 18.dp
                )
        )

        SpoonyButton(
            text = "등록하러 가기",
            size = ButtonSize.Xsmall,
            style = ButtonStyle.Secondary,
            onClick = onClick
        )
    }
}
