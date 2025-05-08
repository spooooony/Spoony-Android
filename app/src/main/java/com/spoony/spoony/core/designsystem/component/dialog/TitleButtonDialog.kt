package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun TitleButtonDialog(
    title: String,
    description: String,
    buttonText: String,
    onDismiss: () -> Unit,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit,
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
                .padding(
                    top = 15.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .noRippleClickable(onClick = onDismiss)
                            .align(Alignment.TopEnd)
                    )

                    Text(
                        text = title,
                        style = SpoonyAndroidTheme.typography.title1,
                        color = SpoonyAndroidTheme.colors.gray900,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                }

                Text(
                    text = description,
                    style = SpoonyAndroidTheme.typography.body2m,
                    color = SpoonyAndroidTheme.colors.gray600,
                    textAlign = TextAlign.Center
                )

                content()

                SpoonyButton(
                    text = buttonText,
                    size = ButtonSize.Medium,
                    style = ButtonStyle.Primary,
                    onClick = onButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun TitleButtonDialogPreview() {
    SpoonyAndroidTheme {
        TitleButtonDialog(
            title = "오늘의 스푼 뽑기",
            description = "\'스푼 뽑기\' 버튼을 누르면\n오늘의 스푼을 획득할 수 있어요.",
            buttonText = "스푼 뽑기",
            onButtonClick = {},
            onDismiss = {}
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
