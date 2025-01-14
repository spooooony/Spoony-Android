package com.spoony.spoony.core.designsystem.component.tag

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.SpoonyColors
import com.spoony.spoony.core.designsystem.type.TagChipColor

@Composable
fun IconTag(
    text: String,
    tagColor: TagChipColor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable (Color) -> Unit)? = null
) {
    val (backgroundColor, iconTextColor) = rememberTagStyle(tagColor)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = backgroundColor)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
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
            style = SpoonyAndroidTheme.typography.caption1m
        )
    }
}

private fun getIconTagStyle(tagColor: TagChipColor, spoonyColor: SpoonyColors): Pair<Color, Color> {
    return when (tagColor) {
        TagChipColor.Main ->
            spoonyColor.main0 to
                spoonyColor.main400
        TagChipColor.Orange ->
            spoonyColor.orange100 to
                spoonyColor.orange400
        TagChipColor.Pink ->
            spoonyColor.pink100 to
                spoonyColor.pink400
        TagChipColor.Green ->
            spoonyColor.green100 to
                spoonyColor.green400
        TagChipColor.Blue ->
            spoonyColor.blue100 to
                spoonyColor.blue400
        TagChipColor.Purple ->
            spoonyColor.purple100 to
                spoonyColor.purple400
        else ->
            spoonyColor.black to
                spoonyColor.white
    }
}

@Composable
private fun rememberTagStyle(tagColor: TagChipColor): Pair<Color, Color> {
    val spoonyColor = SpoonyAndroidTheme.colors

    return remember(tagColor) {
        getIconTagStyle(tagColor, spoonyColor)
    }
}

@Preview
@Composable
private fun IconTagPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TagChipColor.entries
                .filter {
                        color ->
                    color != TagChipColor.Black &&
                        color != TagChipColor.White
                }
                .forEach { color ->
                    IconTag(
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
