package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun UserProfileInfo(
    imageUrl: String,
    name: String,
    location: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    color = SpoonyAndroidTheme.colors.gray500,
                    shape = CircleShape
                ),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column {
            Text(
                text = name,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = location,
                style = SpoonyAndroidTheme.typography.caption1m,
                color = SpoonyAndroidTheme.colors.gray400,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun UserProfileInfoPreview() {
    SpoonyAndroidTheme {
        UserProfileInfo(
            imageUrl = "https://gratisography.com/wp-content/uploads/2024/10/gratisography-cool-cat-800x525.jpg",
            name = "클레오가트라",
            location = "마포구 수저"
        )
    }
}
