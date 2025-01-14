package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.tag.LogoTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.TagSize

@Composable
fun TagTopAppBar(
    count: Int,
    content: @Composable () -> Unit = {},
    tagSize: TagSize = TagSize.Small,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {}
) {
    SpoonyBasicTopAppBar(
        showBackButton = showBackButton,
        onBackButtonClick = onBackButtonClick,
        actions = {
            LogoTag(
                count = count,
                tagSize = tagSize,
                onClick = {},
                modifier = Modifier.padding(end = 20.dp)
            )
        },
        content = content
    )
}

@Preview
@Composable
fun TagTopAppBarPreview() {
    SpoonyAndroidTheme {
        TagTopAppBar(
            count = 99,
            content = {
                Text(
                    text = "홍대입구역",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            showBackButton = false
        )
    }
}
