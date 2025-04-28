package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun FilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    isSelected: Boolean = false
) {
    val borderColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray100
    val textColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray600
    val backgroundColor = if (isSelected) SpoonyAndroidTheme.colors.main0 else SpoonyAndroidTheme.colors.gray0
    val horizontalPadding = if (leftIcon != null || rightIcon != null) 14.dp else 12.dp

    Row(
        modifier = modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = backgroundColor
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (leftIcon != null) {
            leftIcon()
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            color = textColor,
            style = SpoonyAndroidTheme.typography.body2sb
        )
        if (rightIcon != null) {
            Spacer(modifier = Modifier.width(2.dp))
            rightIcon()
        }
    }
}
