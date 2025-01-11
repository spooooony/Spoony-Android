package com.spoony.spoony.core.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun TwoButtonDialog(
    message: String,
    cancelText: String,
    onCancel: () -> Unit,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    BaseDialog(
        message = message,
        content = content,
        onDismiss = onDismiss,
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    cancelText,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onCancel)
                        .background(
                            color = SpoonyAndroidTheme.colors.gray0,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp
                        ),
                    textAlign = TextAlign.Center,
                    style = SpoonyAndroidTheme.typography.body2b,
                    color = SpoonyAndroidTheme.colors.gray600
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    confirmText,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onConfirm)
                        .background(
                            color = SpoonyAndroidTheme.colors.black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp
                        ),
                    textAlign = TextAlign.Center,
                    style = SpoonyAndroidTheme.typography.body2b,
                    color = SpoonyAndroidTheme.colors.white
                )
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
            cancelText = "나중에 먹을래요",
            onCancel = {},
            confirmText = "떠먹기",
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
fun TwoButtonDialogTestPreview() {
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
                TwoButtonDialog(
                    message = "수저 1개를 사용하여 떠먹어 볼까요?",
                    cancelText = "나중에 먹을래요",
                    onCancel = {
                        isDialogVisible = false
                    },
                    confirmText = "떠먹을래요",
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
