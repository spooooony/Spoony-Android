package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
                color = SpoonyAndroidTheme.colors.black
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
