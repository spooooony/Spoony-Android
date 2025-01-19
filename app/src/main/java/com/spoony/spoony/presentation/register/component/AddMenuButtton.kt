package com.spoony.spoony.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun AddMenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .border(1.dp, SpoonyAndroidTheme.colors.gray100, RoundedCornerShape(8.dp))
            .background(SpoonyAndroidTheme.colors.gray0)
            .padding(vertical = 10.dp)
            .noRippleClickable(onClick = onClick)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray400
        )
    }
}

@Preview
@Composable
private fun AddMenuButtonPreview() {
    SpoonyAndroidTheme {
        AddMenuButton(onClick = {})
    }
}
