package com.spoony.spoony.presentation.mypage.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun ProfileHeaderSection(
    imageUrl: String,
    reviewCount: Int,
    followerCount: Int,
    followingCount: Int,
    onFollowerClick: () -> Unit = {},
    onFollowingClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        UrlImage(
            imageUrl = imageUrl,
            shape = CircleShape,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(85.dp)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            NameAndCount(title = "리뷰", count = reviewCount.toString())
            NameAndCount(
                title = "팔로워",
                count = followerCount.toString(),
                onClick = onFollowerClick
            )
            NameAndCount(
                title = "팔로잉",
                count = followingCount.toString(),
                onClick = onFollowingClick
            )
        }
    }
}

@Composable
private fun NameAndCount(
    title: String,
    count: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.noRippleClickable(onClick = onClick)
    ) {
        Text(
            text = title,
            style = SpoonyAndroidTheme.typography.caption1b,
            color = SpoonyAndroidTheme.colors.gray400
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = count,
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileHeaderSectionPreview() {
    SpoonyAndroidTheme {
        ProfileHeaderSection(
            imageUrl = "",
            reviewCount = 100,
            followerCount = 500,
            followingCount = 200,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}
