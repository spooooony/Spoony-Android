package com.spoony.spoony.presentation.myPage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.tag.LogoTag
import com.spoony.spoony.core.designsystem.component.topappbar.SpoonyBasicTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.rotateClick

@Composable
fun MyPageTopAppBar(
    count: Int,
    onLogoClick: () -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SpoonyBasicTopAppBar(
        content = {
            LogoTag(
                count = count,
                tagSize = TagSize.Small,
                modifier = Modifier.rotateClick(onClick = onLogoClick)
            )
        },
        actions = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting_24),
                contentDescription = null,
                tint = SpoonyAndroidTheme.colors.gray500,
                modifier = Modifier.noRippleClickable(onClick = onIconClick)
            )
        },
        modifier = modifier.background(white).padding(vertical = 2.dp)
    )
}

@Preview
@Composable
private fun MyPageTopAppBarPreview() {
    SpoonyAndroidTheme {
        MyPageTopAppBar(
            count = 99,
            onLogoClick = { },
            onIconClick = { },
            modifier = Modifier
        )
    }
}
