package com.spoony.spoony.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun BasicSnackbar(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(
                bottom = 20.dp,
                start = 20.dp,
                end = 20.dp
            )
            .alpha(0.88f)
            .clip(RoundedCornerShape(10.dp))
            .background(SpoonyAndroidTheme.colors.gray600)
    ) {
        content()
    }
}
