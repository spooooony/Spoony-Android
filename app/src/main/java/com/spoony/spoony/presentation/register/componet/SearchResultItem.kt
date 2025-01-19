package com.spoony.spoony.presentation.register.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun SearchResultItem(
    placeName: String,
    placeRoadAddress: String,
    onResultClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .border(1.dp, SpoonyAndroidTheme.colors.gray100, RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp)
            .noRippleClickable(onClick = onResultClick)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_pin_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = SpoonyAndroidTheme.colors.gray900
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column {
                Text(
                    text = placeName,
                    style = SpoonyAndroidTheme.typography.body2b,
                    color = SpoonyAndroidTheme.colors.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = placeRoadAddress,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_filled_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray400,
            modifier = Modifier.noRippleClickable(onClick = onDeleteClick)
        )
    }
}

@Preview
@Composable
private fun SearchResultPreview() {
    SpoonyAndroidTheme {
        Box(
            modifier = Modifier
                .width(340.dp)
                .padding(vertical = 20.dp)
        ) {
            SearchResultItem(
                placeName = "테스트점",
                placeRoadAddress = "우리집 어쩌구",
                onResultClick = {},
                onDeleteClick = {}
            )
        }
    }
}
