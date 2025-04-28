package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.dropdown.IconDropdown
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExploreItem(
    review: String,
    textColor: Color,
    backgroundColor: Color,
    onMenuItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    iconUrl: String? = "",
    tagText: String? = "",
    date: String? = "",
    username: String?,
    placeSpoon: String?,
    addMapCount: Int? = 0,
    onClick: () -> Unit = {},
    imageList: ImmutableList<String>? = persistentListOf(),
    menuItems: ImmutableList<String> = persistentListOf("신고하기")
) {
    Column(
        modifier = modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(8.dp))
            .background(SpoonyAndroidTheme.colors.gray0)
            .padding(
                horizontal = 10.dp,
                vertical = 18.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconTag(
                text = tagText ?: "",
                iconUrl = iconUrl ?: "",
                textColor = textColor,
                backgroundColor = backgroundColor
            )
            Spacer(modifier = Modifier.weight(1f))
            IconDropdown(
                menuItems,
                onMenuItemClick
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = username ?: "",
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$placeSpoon 스푼",
                style = SpoonyAndroidTheme.typography.caption2m,
                color = SpoonyAndroidTheme.colors.gray500
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = review,
            style = SpoonyAndroidTheme.typography.caption1m,
            color = SpoonyAndroidTheme.colors.black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SpoonyAndroidTheme.colors.white)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        ImageGrid(imageList = imageList ?: persistentListOf())

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add_map_main400_24),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${addMapCount}명이 지도에 저장했어요!",
                    style = SpoonyAndroidTheme.typography.caption2b,
                    color = SpoonyAndroidTheme.colors.main400
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = date ?: "",
                style = SpoonyAndroidTheme.typography.caption2m,
                color = SpoonyAndroidTheme.colors.gray400
            )
        }
    }
}

@Composable
private fun ImageGrid(imageList: ImmutableList<String>) {
    if (imageList.isEmpty()) return

    val imageShape = when (imageList.size) {
        1 -> 10.dp
        2 -> 8.dp
        else -> 6.dp
    }

    val arrangement = when (imageList.size) {
        1 -> Arrangement.Center
        2 -> Arrangement.spacedBy(9.dp)
        else -> Arrangement.spacedBy(7.dp)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement
    ) {
        imageList.take(3).forEachIndexed { index, imageUrl ->
            UrlImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(imageShape),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
    Spacer(modifier = Modifier.height(18.dp))
}
