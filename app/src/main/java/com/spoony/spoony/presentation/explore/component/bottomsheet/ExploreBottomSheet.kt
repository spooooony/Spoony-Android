package com.spoony.spoony.presentation.explore.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun ExploreBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDragHandle: () -> Unit
) {
}

@Preview
@Composable
private fun PreviewExploreBottomSheet() {
    SpoonyAndroidTheme {
        ExploreBottomSheet(
            onDismissRequest = { },
            onDragHandle = { }
        )
    }
}
