package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun ScoopDialog(
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoony_detail_use))

    TwoButtonDialog(
        message = "수저 1개를 사용하여 떠먹어 볼까요?",
        negativeText = "아니요",
        onClickNegative = onClickNegative,
        positiveText = "떠먹을래요",
        onClickPositive = onClickPositive,
        onDismiss = onClickNegative,
        content = {
            LottieAnimation(
                modifier = Modifier
                    .sizeIn(minHeight = 150.dp),
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever
            )
        }
    )
}

@Preview
@Composable
private fun ScoopDialogPreview() {
    SpoonyAndroidTheme {
        ScoopDialog(
            onClickPositive = {},
            onClickNegative = {}
        )
    }
}
