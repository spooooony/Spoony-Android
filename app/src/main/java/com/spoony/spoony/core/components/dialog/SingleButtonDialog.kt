package com.spoony.spoony.core.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun SingleButtonDialog(
    message: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    BaseDialog(
        message = message,
        content = content,
        onDismiss = onDismiss,
        buttons = {
            Text(
                confirmText,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onConfirm)
                    .background(
                        color = SpoonyAndroidTheme.colors.black,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(
                        vertical = 18.dp,
                        horizontal = 16.dp
                    ),
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.white,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Preview
@Composable
private fun SingleButtonDialogPreview() {
    SpoonyAndroidTheme {
        SingleButtonDialog(
            message = "수저 1개를 획득했어요!\n이제 새로운 장소를 떠먹으러 가볼까요?",
            confirmText = "갈래요!",
            onConfirm = {},
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
fun SingleButtonDialogTestPreview() {
    var isDialogVisible by remember { mutableStateOf(false) }

    SpoonyAndroidTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                SingleButtonDialog(
                    message = "수저 1개를 획득했어요!\n이제 새로운 장소를 떠먹으러 가볼까요?",
                    confirmText = "갈래요!",
                    onConfirm = {
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
