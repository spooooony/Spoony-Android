package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun ExploreItem(
    username: String,
    placeSpoon: String,
    review: String,
    addMapCount: Int,
    iconUrl: String,
    tagText: String,
    textColorHex: String,
    backgroundColorHex: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SpoonyAndroidTheme.colors.gray0)
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 15.dp,
                bottom = 18.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconTag(
                text = tagText,
                iconUrl = iconUrl,
                textColorHex = textColorHex,
                backgroundColorHex = backgroundColorHex
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add_map_gray400_24),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(16.dp)
            )
            Text(
                text = addMapCount.toString(),
                style = SpoonyAndroidTheme.typography.caption2b,
                color = SpoonyAndroidTheme.colors.gray500,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(vertical = 9.dp)
        ) {
            Text(
                text = username,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black,
                modifier = Modifier
                    .padding(start = 5.dp, end = 4.dp)
            )

            Text(
                text = "$placeSpoon 수저",
                style = SpoonyAndroidTheme.typography.caption2m,
                color = SpoonyAndroidTheme.colors.gray500
            )
        }

        Text(
            text = review,
            style = SpoonyAndroidTheme.typography.caption1m,
            color = SpoonyAndroidTheme.colors.black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SpoonyAndroidTheme.colors.white)
                .padding(8.dp)
        )
    }
}
