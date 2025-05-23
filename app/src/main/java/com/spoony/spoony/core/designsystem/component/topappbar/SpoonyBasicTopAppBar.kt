package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun SpoonyBasicTopAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
        ) {
            navigationIcon()
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
