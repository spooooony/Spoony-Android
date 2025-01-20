package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun SingleButtonDialog(
    message: String,
    text: String,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
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
                SpoonyButton(
                    text = text,
                    size = ButtonSize.Large,
                    style = buttonStyle,
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview
@Composable
private fun SingleButtonDialogPreview() {
    SpoonyAndroidTheme {
        SingleButtonDialog(
            message = "수저 1개를 획득했어요!\n이제 새로운 장소를 떠먹으러 가볼까요?",
            text = "갈래요!",
            onClick = {},
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
                    text = "갈래요!",
                    onClick = {
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
