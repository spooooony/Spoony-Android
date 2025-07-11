package com.spoony.spoony.presentation.gourmet.map.component.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun MapListItem(
    placeName: String,
    address: String,
    review: String,
    imageUrl: String,
    categoryIconUrl: String,
    categoryName: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = SpoonyAndroidTheme.typography
    val color = SpoonyAndroidTheme.colors

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = placeName,
                    style = typography.body1b,
                    color = color.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, false)
                )
                IconTag(
                    text = categoryName,
                    iconUrl = categoryIconUrl,
                    backgroundColor = backgroundColor,
                    textColor = textColor,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }

            Text(
                text = address,
                style = typography.caption1m,
                color = color.gray600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

            Text(
                text = review,
                style = typography.body2m,
                color = color.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color.gray0),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 10.dp)
            )
        }

        UrlImage(
            imageUrl = imageUrl,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(98.dp)
        )
    }
}
