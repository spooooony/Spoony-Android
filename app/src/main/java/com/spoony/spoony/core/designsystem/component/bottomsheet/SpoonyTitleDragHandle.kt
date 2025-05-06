package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun SpoonyTitleDragHandle(
    onClick: () -> Unit,
    text: String = "지역 선택"
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .padding(top = 12.dp)
    ) {
        Text(
            text = text,
            style = SpoonyAndroidTheme.typography.body1b,
            color = SpoonyAndroidTheme.colors.gray900,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray400,
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    end = 20.dp
                )
                .noRippleClickable(onClick = onClick)
        )
    }
}
