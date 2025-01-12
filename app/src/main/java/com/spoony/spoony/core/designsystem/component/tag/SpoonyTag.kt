package com.spoony.spoony.core.designsystem.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.TagType

@Composable
fun SpoonyTag(
    count: Int,
    onClick: () -> Unit,
    tagType: TagType = TagType.Small,
    modifier: Modifier = Modifier
) {
    val spoonTypography = SpoonyAndroidTheme.typography
    val textSize = remember(tagType) {
        when (tagType) {
            TagType.Large -> spoonTypography.body1sb
            TagType.Small -> spoonTypography.body2sb
        }
    }

    val paddingValues = remember(tagType) {
        when (tagType) {
            TagType.Large -> PaddingValues(start = 12.dp, end = 4.dp, top = 5.dp, bottom = 4.dp)
            TagType.Small -> PaddingValues(start = 8.dp, end = 2.dp, top = 4.dp, bottom = 4.dp)
        }
    }

    val betweenPaddingValues = remember(tagType) {
        when (tagType) {
            TagType.Large -> 6.dp
            TagType.Small -> 5.dp
        }
    }

    val verticalAlignment = remember(tagType) {
        when (tagType) {
            TagType.Large -> Alignment.Top
            TagType.Small -> Alignment.CenterVertically
        }
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        SpoonyAndroidTheme.colors.gray800,
                        SpoonyAndroidTheme.colors.gray900,
                        SpoonyAndroidTheme.colors.black
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
            .clickable { onClick() }
            .padding(paddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = verticalAlignment
    ) {
        Text(
            text = count.toString(),
            style = textSize,
            color = SpoonyAndroidTheme.colors.white
        )
        Spacer(modifier = Modifier.width(betweenPaddingValues))
        Icon(
            painter = painterResource(id = R.drawable.ic_tag_spoon_20),
            contentDescription = "Arrow Icon",
            tint = Color.Unspecified
        )
    }
}

private class TagTypeProvider : PreviewParameterProvider<TagType> {
    override val values: Sequence<TagType> = sequenceOf(
        TagType.Large,
        TagType.Small
    )
}

@Preview
@Composable
private fun SpoonyTagPreview(
    @PreviewParameter(TagTypeProvider::class) type: TagType
) {
    SpoonyAndroidTheme {
        SpoonyTag(
            count = 99,
            tagType = type,
            onClick = {}
        )
    }
}
