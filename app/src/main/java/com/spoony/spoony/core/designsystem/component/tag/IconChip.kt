package com.spoony.spoony.core.designsystem.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.IconChipColor
import com.spoony.spoony.core.designsystem.type.TagSize

@Composable
fun IconChip(
    text: String,
    tagSize: TagSize,
    tagColor: IconChipColor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable (Color) -> Unit)? = null
) {
    val spoonTypography = SpoonyAndroidTheme.typography
    val spoonyColor = SpoonyAndroidTheme.colors
    val (textStyle, paddingValues) = remember(tagSize) {
        when (tagSize) {
            TagSize.Large ->
                spoonTypography.body2sb to
                    PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            TagSize.Small ->
                spoonTypography.caption1m to
                    PaddingValues(horizontal = 10.dp, vertical = 4.dp)
        }
    }
    val (backgroundBrush, iconTextColor) = remember(tagSize, tagColor) {
        when (tagSize) {
            TagSize.Large -> when (tagColor) {
                IconChipColor.Black -> Brush.linearGradient(
                    colors = listOf(
                        spoonyColor.gray800,
                        spoonyColor.gray900,
                        spoonyColor.black
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                ) to spoonyColor.white
                IconChipColor.White ->
                    SolidColor(spoonyColor.white) to
                        spoonyColor.gray600
                IconChipColor.Main ->
                    SolidColor(spoonyColor.main400) to
                        spoonyColor.white
                else ->
                    SolidColor(spoonyColor.white) to
                        spoonyColor.black
            }
            TagSize.Small -> when (tagColor) {
                IconChipColor.Main ->
                    SolidColor(spoonyColor.main0) to
                        spoonyColor.main400
                IconChipColor.Orange ->
                    SolidColor(spoonyColor.orange100) to
                        spoonyColor.orange400
                IconChipColor.Pink ->
                    SolidColor(spoonyColor.pink100) to
                        spoonyColor.pink400
                IconChipColor.Green ->
                    SolidColor(spoonyColor.green100) to
                        spoonyColor.green400
                IconChipColor.Blue ->
                    SolidColor(spoonyColor.blue100) to
                        spoonyColor.blue400
                IconChipColor.Purple ->
                    SolidColor(spoonyColor.purple100) to
                        spoonyColor.purple400
                else ->
                    SolidColor(spoonyColor.black) to
                        spoonyColor.white
            }
        }
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush = backgroundBrush)
            .clickable(onClick = onClick)
            .padding(paddingValues),
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
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun IconChipPreview(
    @PreviewParameter(TagSizeProvider::class) size: TagSize
) {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconChipColor.entries
                .filter { color ->
                    if (size == TagSize.Large) {
                        color == IconChipColor.Black ||
                            color == IconChipColor.White ||
                            color == IconChipColor.Main
                    } else {
                        color != IconChipColor.Black && color != IconChipColor.White
                    }
                }
                .forEach { color ->
                    IconChip(
                        text = "chip",
                        tagSize = size,
                        tagColor = color,
                        onClick = { },
                        icon = { tint ->
                            Icon(
                                painter = painterResource(R.drawable.ic_american_24), // 예시로 Star 아이콘 사용
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
