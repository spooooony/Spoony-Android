package com.spoony.spoony.presentation.explore.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.spoony.spoony.presentation.explore.search.UserInfo

@Composable
fun ExploreSearchUserItem(
    userInfo: UserInfo,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 20.dp
            )
            .noRippleClickable{ onItemClick(userInfo.userId) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UrlImage(
            imageUrl = userInfo.imageUrl,
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = userInfo.nickname,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${userInfo.region} 스푼",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400
            )
        }
    }
}