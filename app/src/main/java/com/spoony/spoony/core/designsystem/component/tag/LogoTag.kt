package com.spoony.spoony.core.designsystem.component.tag

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.SpoonyTypography
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.spoonyGradient

@Composable
fun LogoTag(
    count: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tagSize: TagSize = TagSize.Small
) {
    val spoonTypography = SpoonyAndroidTheme.typography
    val (betweenValues, paddingValues) = remember(tagSize) {
        getLogoTagPaddingAndSpacing(tagSize)
    }

    val (textSize, verticalAlignment) = remember(tagSize) {
        getLogoTagStyleAndAlignment(tagSize, spoonTypography)
    }

    Row(
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .clip(RoundedCornerShape(20.dp))
            .spoonyGradient(20.dp)
            .padding(paddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = verticalAlignment
    ) {
        Text(
            text = count.toString(),
            style = textSize,
            color = SpoonyAndroidTheme.colors.white
        )
        Spacer(modifier = Modifier.width(betweenValues))
        Icon(
            painter = painterResource(id = R.drawable.ic_tag_spoon_20),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

private fun getLogoTagPaddingAndSpacing(tagSize: TagSize): Pair<Dp, PaddingValues> {
    return when (tagSize) {
        TagSize.Large ->
            6.dp to PaddingValues(start = 12.dp, end = 4.dp, top = 5.dp, bottom = 4.dp)

        TagSize.Small ->
            5.dp to PaddingValues(start = 8.dp, end = 2.dp, top = 4.dp, bottom = 4.dp)
    }
}

private fun getLogoTagStyleAndAlignment(tagSize: TagSize, spoonTypography: SpoonyTypography): Pair<TextStyle, Alignment.Vertical> {
    return when (tagSize) {
        TagSize.Large ->
            spoonTypography.body1sb to Alignment.Top

        TagSize.Small ->
            spoonTypography.body2sb to Alignment.CenterVertically
    }
}

class TagSizeProvider : PreviewParameterProvider<TagSize> {
    override val values: Sequence<TagSize> = sequenceOf(
        TagSize.Large,
        TagSize.Small
    )
}

@Preview
@Composable
private fun LogoTagPreview(
    @PreviewParameter(TagSizeProvider::class) size: TagSize
) {
    SpoonyAndroidTheme {
        LogoTag(
            count = 99,
            tagSize = size
        )
    }
}
