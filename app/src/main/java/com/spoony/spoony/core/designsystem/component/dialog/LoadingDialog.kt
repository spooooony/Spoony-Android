package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun LoadingDialog(
    title: String,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 35.dp)
                .background(
                    color = SpoonyAndroidTheme.colors.white,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(top = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = SpoonyAndroidTheme.typography.body1sb,
                    color = SpoonyAndroidTheme.colors.gray900,
                    textAlign = TextAlign.Center
                )

                content()
            }
        }
    }
}

@Preview
@Composable
private fun LoadingDialogPreview() {
    SpoonyAndroidTheme {
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
}
