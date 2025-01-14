package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.padding
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
    tagSize: TagSize = TagSize.Small
) {
    SpoonyBasicTopAppBar(
        showBackButton = true,
        actions = {
            LogoTag(
                count = count,
                tagSize = tagSize,
                onClick = {},
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    )
}

@Preview
@Composable
fun TagTopAppBarPreview() {
    SpoonyAndroidTheme {
        TagTopAppBar(
            count = 99
        )
    }
}
