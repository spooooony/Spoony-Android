package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.coroutines.delay

private enum class SpoonDrawDialogState {
    DRAW, LOADING, RESULT
}

@Composable
fun SpoonDrawDialog(
    onDismiss: () -> Unit,
    onSpoonDrawButtonClick: () -> SpoonDrawModel,
    onConfirmButtonClick: () -> Unit
) {
    var dialogState by remember { mutableStateOf(SpoonDrawDialogState.DRAW) }
    var drawResult by remember { mutableStateOf(SpoonDrawModel()) }

    LaunchedEffect(dialogState) {
        if (dialogState == SpoonDrawDialogState.LOADING) {
            delay(3000)
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
                    drawResult = onSpoonDrawButtonClick()
                },
                onDismiss = onDismiss
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(226.dp)
                        .background(color = SpoonyAndroidTheme.colors.gray400)
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
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
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
