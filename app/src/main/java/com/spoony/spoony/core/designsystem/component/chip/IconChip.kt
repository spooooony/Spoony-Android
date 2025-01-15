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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.SpoonyColors
import com.spoony.spoony.core.designsystem.type.ChipColor

@Composable
fun IconChip(
    text: String,
    tagColor: ChipColor,
    iconUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (backgroundBrush, iconTextColor) = rememberChipStyle(tagColor)
    val context = LocalContext.current
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
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier.size(16.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = iconTextColor,
            style = SpoonyAndroidTheme.typography.body2sb
        )
    }
}

private fun getChipStyle(tagColor: ChipColor, spoonyColor: SpoonyColors): Pair<Brush, Color> {
    return when (tagColor) {
        ChipColor.Black -> Brush.linearGradient(
            colors = listOf(
                spoonyColor.gray800,
                spoonyColor.gray900,
                spoonyColor.black
            ),
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f)
        ) to spoonyColor.white
        ChipColor.White ->
            SolidColor(spoonyColor.gray0) to
                spoonyColor.gray600
        ChipColor.Main ->
            SolidColor(spoonyColor.main400) to
                spoonyColor.white
    }
}

@Composable
private fun rememberChipStyle(tagColor: ChipColor): Pair<Brush, Color> {
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
            IconChip(
                text = "chip",
                tagColor = ChipColor.Black,
                onClick = { },
                iconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8"
            )
            IconChip(
                text = "chip",
                tagColor = ChipColor.White,
                onClick = { },
                iconUrl = "https://github.com/user-attachments/assets/1e0bc0e5-a1c6-44e8-b6f4-f47aced44441"
            )
            IconChip(
                text = "chip",
                tagColor = ChipColor.Main,
                onClick = { },
                iconUrl = "https://github.com/user-attachments/assets/c3cd06c9-7771-48c3-a0ee-0128d14135fd"
            )
        }
    }
}
