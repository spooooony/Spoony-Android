package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun TitleTopAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white
) {
    SpoonyBasicTopAppBar(
        showBackButton = true,
        onBackButtonClick = onBackButtonClick,
        content = {
            Text(
                text = title,
                style = SpoonyAndroidTheme.typography.title2b,
                color = SpoonyAndroidTheme.colors.black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 44.dp),
                textAlign = TextAlign.Center
            )
        },
        modifier = modifier,
        backgroundColor = backgroundColor
    )
}

@Preview
@Composable
private fun TitleTopAppBarPreview() {
    SpoonyAndroidTheme {
        TitleTopAppBar(
            title = "신고하기",
            onBackButtonClick = {}
        )
    }
}
