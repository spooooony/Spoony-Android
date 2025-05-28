package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.coroutines.launch

private enum class SpoonDrawDialogState {
    DRAW, LOADING, RESULT
}

@Composable
fun SpoonDrawDialog(
    onDismiss: () -> Unit,
    onSpoonDrawButtonClick: suspend () -> SpoonDrawModel,
    onConfirmButtonClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var dialogState by remember { mutableStateOf(SpoonDrawDialogState.DRAW) }
    var drawResult by remember { mutableStateOf(SpoonDrawModel()) }

    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoony_spoon_draw_shake))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(lottieComposition, dialogState) {
        if (dialogState == SpoonDrawDialogState.LOADING && lottieComposition != null) {
            lottieAnimatable.animate(
                composition = lottieComposition,
                iterations = 2
            )
            dialogState = SpoonDrawDialogState.RESULT
        }
    }

    when (dialogState) {
        SpoonDrawDialogState.DRAW -> {
            TitleButtonDialog(
                title = "오늘의 스푼 뽑기",
                description = "\'스푼 뽑기\' 버튼을 누르면\n오늘의 스푼을 획득할 수 있어요.",
                buttonText = "스푼 뽑기",
                onButtonClick = {
                    dialogState = SpoonDrawDialogState.LOADING
                    coroutineScope.launch {
                        drawResult = onSpoonDrawButtonClick()
                    }
                },
                onDismiss = onDismiss
            ) {
                Image(
                    painter = painterResource(R.drawable.img_spoon_draw_pick),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 248.dp, height = 168.dp)
                )
            }
        }

        SpoonDrawDialogState.LOADING -> {
            LoadingDialog(
                title = "스푼을 뽑고 있어요..."
            ) {
                LottieAnimation(
                    modifier = Modifier.height(226.dp),
                    composition = lottieComposition,
                    progress = { lottieAnimatable.progress }
                )
            }
        }

        SpoonDrawDialogState.RESULT -> {
            TitleButtonDialog(
                title = "${drawResult.spoonName} 획득",
                description = "축하해요!\n총 ${drawResult.spoonAmount}개의 스푼을 적립했어요.",
                buttonText = "확인",
                onButtonClick = onConfirmButtonClick,
                onDismiss = onDismiss
            ) {
                UrlImage(
                    imageUrl = drawResult.spoonImage,
                    modifier = Modifier
                        .size(width = 248.dp, height = 168.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SpoonDrawDialogPreview() {
    SpoonyAndroidTheme {
        SpoonDrawDialog(
            onDismiss = {},
            onSpoonDrawButtonClick = {
                SpoonDrawModel(
                    drawId = 1,
                    spoonTypeId = 1,
                    spoonName = "다이아스푼",
                    spoonAmount = 4,
                    spoonImage = "",
                    localDate = ""
                )
            },
            onConfirmButtonClick = {}
        )
    }
}
