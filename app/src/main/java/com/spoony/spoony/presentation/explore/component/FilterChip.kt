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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val spoonyAndroidThemeColors = SpoonyAndroidTheme.colors
    val borderColor by remember {
        derivedStateOf {
            if (isSelected) spoonyAndroidThemeColors.main400 else spoonyAndroidThemeColors.gray100
        }
    }
    val textColor by remember {
        derivedStateOf {
            if (isSelected) spoonyAndroidThemeColors.main400 else spoonyAndroidThemeColors.gray600
        }
    }
    val backgroundColor by remember {
        derivedStateOf {
            if (isSelected) spoonyAndroidThemeColors.main0 else spoonyAndroidThemeColors.gray0
        }
    }
    val horizontalPadding by remember {
        derivedStateOf {
            if (leftIcon != null || rightIcon != null) 14.dp else 12.dp
        }
    }

    Row(
        modifier = modifier
            .noRippleClickable(onClick)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        leftIcon?.let {
            it()
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            color = textColor,
            style = SpoonyAndroidTheme.typography.body2sb
        )
        rightIcon?.let {
            Spacer(modifier = Modifier.width(2.dp))
            it()
        }
    }
}
