package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun TwoButtonDialog(
    message: String,
    negativeText: String,
    onClickNegative: () -> Unit,
    positiveText: String,
    onClickPositive: () -> Unit,
    onDismiss: () -> Unit,
    buttonStyle: ButtonStyle = ButtonStyle.Secondary,
    content: @Composable () -> Unit = {}
) {
    SpoonyBasicDialog(
        onDismiss = onDismiss,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Text(
                    text = message,
                    style = SpoonyAndroidTheme.typography.body1b,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SpoonyButton(
                        text = negativeText,
                        onClick = onClickNegative,
                        style = ButtonStyle.Tertiary,
                        size = ButtonSize.Xsmall,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    SpoonyButton(
                        text = positiveText,
                        onClick = onClickPositive,
                        style = buttonStyle,
                        size = ButtonSize.Xsmall,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun TwoButtonDialogPreview() {
    SpoonyAndroidTheme {
        TwoButtonDialog(
            message = "수저 1개를 사용하여 떠먹어 볼까요?",
            negativeText = "아니요",
            onClickNegative = {},
            positiveText = "떠먹을래요",
            onClickPositive = {},
            onDismiss = {},
            content = {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(SpoonyAndroidTheme.colors.gray300)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TwoButtonDialogTestPreview() {
    var isDialogVisible by remember { mutableStateOf(false) }

    SpoonyAndroidTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    isDialogVisible = true
                }
            ) {
                Text("다이얼로그 열기")
            }

            if (isDialogVisible) {
                TwoButtonDialog(
                    message = "수저 1개를 사용하여 떠먹어 볼까요?",
                    negativeText = "아니요",
                    onClickNegative = {
                        isDialogVisible = false
                    },
                    positiveText = "떠먹을래요",
                    onClickPositive = {
                        isDialogVisible = false
                    },
                    onDismiss = {
                        isDialogVisible = false
                    },
                    content = {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .background(SpoonyAndroidTheme.colors.gray300)
                        )
                    }
                )
            }
        }
    }
}
