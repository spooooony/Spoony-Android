package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun SpoonyBasicTopAppBar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white,
    onBackButtonClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
        ) {
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
            content()
        }
        actions()
    }
}

@Preview
@Composable
private fun SpoonyBasicTopAppBarPreview() {
    SpoonyAndroidTheme {
        SpoonyBasicTopAppBar()
    }
}
