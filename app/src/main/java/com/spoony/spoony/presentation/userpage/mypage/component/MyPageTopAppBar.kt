package com.spoony.spoony.presentation.userpage.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.tag.LogoTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.util.extension.rotateClick

@Composable
fun MyPageTopAppBar(
    spoonCount: Int,
    onLogoClick: () -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(white)
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoTag(
            count = spoonCount,
            tagSize = TagSize.Small,
            onClick = onLogoClick
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier.rotateClick(onClick = onIconClick)
        )
    }
}

@Preview
@Composable
private fun MyPageTopAppBarPreview() {
    SpoonyAndroidTheme {
        MyPageTopAppBar(
            spoonCount = 99,
            onLogoClick = { },
            onIconClick = { },
            modifier = Modifier
        )
    }
}
