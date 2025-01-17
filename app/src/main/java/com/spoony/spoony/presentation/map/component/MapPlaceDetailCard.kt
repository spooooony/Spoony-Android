package com.spoony.spoony.presentation.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MapPlaceDetailCard(
    placeName: String,
    username: String,
    placeSpoon: String,
    review: String,
    addMapCount: Int,
    imageUrlList: ImmutableList<String>,
    categoryIconUrl: String,
    categoryName: String,
    textColor: String,
    backgroundColor: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .drawWithCache {
                val path = Path()
                path.moveTo(size.width / 2f, 0f)
                path.lineTo(size.width / 2f + 8.dp.toPx(), 14.dp.toPx())
                path.lineTo(size.width / 2f - 8.dp.toPx(), 14.dp.toPx())
                path.close()
                onDrawBehind {
                    drawPath(path, Color.White)
                }
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dp.Hairline),
            modifier = Modifier
                .padding(top = 14.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(SpoonyAndroidTheme.colors.white)
        ) {
            imageUrlList.forEachIndexed { index, url ->
                key(index) {
                    UrlImage(
                        imageUrl = url,
                        modifier = Modifier
                            .height(103.dp)
                            .weight(1f)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(SpoonyAndroidTheme.colors.white)
                .padding(15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                Text(
                    text = placeName,
                    style = SpoonyAndroidTheme.typography.body1b,
                    color = SpoonyAndroidTheme.colors.gray900
                )
                IconTag(
                    text = categoryName,
                    backgroundColorHex = backgroundColor,
                    textColorHex = textColor,
                    iconUrl = categoryIconUrl,
                    modifier = Modifier
                        .padding(start = 6.dp)
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpoonyAndroidTheme.colors.gray0, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = username,
                        style = SpoonyAndroidTheme.typography.body2sb,
                        color = SpoonyAndroidTheme.colors.gray900
                    )
                    Text(
                        text = "$placeSpoon 수저",
                        style = SpoonyAndroidTheme.typography.caption1m,
                        color = SpoonyAndroidTheme.colors.gray600,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
                Text(
                    text = review,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.black,
                    modifier = Modifier
                        .padding(top = 6.dp)
                )
            }
        }
    }
}
