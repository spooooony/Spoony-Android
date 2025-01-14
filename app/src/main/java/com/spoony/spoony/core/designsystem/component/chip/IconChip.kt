package com.spoony.spoony.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.SpoonyColors
import com.spoony.spoony.core.designsystem.type.TagChipColor

@Composable
fun IconChip(
    text: String,
    tagColor: TagChipColor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable (Color) -> Unit)? = null
) {
    val (backgroundBrush, iconTextColor) = rememberChipStyle(tagColor)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush = backgroundBrush)
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = SpoonyAndroidTheme.colors.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            icon.invoke(iconTextColor)
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            color = iconTextColor,
            style = SpoonyAndroidTheme.typography.body2sb
        )
    }
}

private fun getChipStyle(tagColor: TagChipColor, spoonyColor: SpoonyColors): Pair<Brush, Color> {
    return when (tagColor) {
        TagChipColor.Black -> Brush.linearGradient(
            colors = listOf(
                spoonyColor.gray800,
                spoonyColor.gray900,
                spoonyColor.black
            ),
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f)
        ) to spoonyColor.white
        TagChipColor.White ->
            SolidColor(spoonyColor.gray0) to
                spoonyColor.gray600
        TagChipColor.Main ->
            SolidColor(spoonyColor.main400) to
                spoonyColor.white
        else ->
            SolidColor(spoonyColor.white) to
                spoonyColor.black
    }
}

@Composable
private fun rememberChipStyle(tagColor: TagChipColor): Pair<Brush, Color> {
    val spoonyColor = SpoonyAndroidTheme.colors

    return remember(tagColor) {
        getChipStyle(tagColor, spoonyColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun IconChipPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TagChipColor.entries
                .filter {
                        color ->
                    color == TagChipColor.Black ||
                        color == TagChipColor.White ||
                        color == TagChipColor.Main
                }
                .forEach { color ->
                    IconChip(
                        text = "chip",
                        tagColor = color,
                        onClick = { },
                        icon = { tint ->
                            Icon(
                                painter = painterResource(R.drawable.ic_american_24),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = tint
                            )
                        }
                    )
                }
        }
    }
}
