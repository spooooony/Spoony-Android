package com.spoony.spoony.presentation.exploreSearch.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.exploreSearch.model.ExploreSearchUserModel

@Composable
fun ExploreSearchUserItem(
    userInfo: ExploreSearchUserModel,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onItemClick(userInfo.userId) }
            .padding(
                vertical = 8.dp,
                horizontal = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UrlImage(
            imageUrl = userInfo.userProfileUrl,
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = userInfo.userName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black
            )
            if (userInfo.userRegion.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "서울 ${userInfo.userRegion} 스푼",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.gray400
                )
            }
        }
    }
}
