package com.spoony.spoony.presentation.setting.block.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.black

@Composable
fun BlockUserItem(
    imageUrl: String,
    userName: String,
    region: String,
    isBlocking: Boolean,
    onBlockButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            UrlImage(
                imageUrl = imageUrl,
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userName,
                    style = SpoonyAndroidTheme.typography.body2sb,
                    color = black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = region,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        BlockButton(
            isBlocking = isBlocking,
            onClick = onBlockButtonClick,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlockUserItemPreview() {
    SpoonyAndroidTheme {
        BlockUserItem(
            imageUrl = "",
            userName = "순두부",
            region = "우리집 우리집 우리집 우리집 우리집 우리집 우리집 우리집 우리집 우리집",
            isBlocking = false,
            onBlockButtonClick = { }
        )
    }
}
