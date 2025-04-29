package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun ExploreFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = SpoonyAndroidTheme.typography.caption1m
) {
    val borderColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray100
    val textColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray500
    val backgroundColor = if (isSelected) SpoonyAndroidTheme.colors.main0 else SpoonyAndroidTheme.colors.gray0
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
                horizontal = 14.dp,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}
