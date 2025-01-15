package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonyBasicTopAppBar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = content,
        modifier = modifier,
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = onBackButtonClick
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_24),
                        contentDescription = null,
                        tint = SpoonyAndroidTheme.colors.gray700,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
        },
        actions = actions
    )
}

@Preview
@Composable
private fun SpoonyBasicTopAppBarPreview() {
    SpoonyAndroidTheme {
        SpoonyBasicTopAppBar()
    }
}
