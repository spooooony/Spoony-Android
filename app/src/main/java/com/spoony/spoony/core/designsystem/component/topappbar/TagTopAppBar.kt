package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.tag.LogoTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun TagTopAppBar(
    count: Int,
    tagSize: TagSize = TagSize.Small,
    showBackButton: Boolean = false,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white,
    onBackButtonClick: () -> Unit = {},
    onLogoTagClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    SpoonyBasicTopAppBar(
        navigationIcon = {
            if (showBackButton) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(32.dp)
                        .noRippleClickable(onBackButtonClick)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_24),
                        contentDescription = null,
                        tint = SpoonyAndroidTheme.colors.gray700
                    )
                }
            }
        },
        actions = {
            LogoTag(
                count = count,
                tagSize = tagSize,
                modifier = Modifier
                    .padding(end = 20.dp),
                onClick = onLogoTagClick
            )
        },
        content = {
            if (!showBackButton) {
                Spacer(Modifier.width(20.dp))
            }
            content()
        },
        backgroundColor = backgroundColor
    )
}

@Preview
@Composable
private fun TagTopAppBarPreview() {
    SpoonyAndroidTheme {
        Column {
            TagTopAppBar(
                count = 99,
                showBackButton = true
            )

            TagTopAppBar(
                count = 99,
                showBackButton = false
            ) {
                Text(
                    text = "홍대입구역"
                )
            }

            TagTopAppBar(
                count = 99,
                showBackButton = true
            ) {
                Text(
                    text = "홍대입구역"
                )
            }
        }
    }
}
