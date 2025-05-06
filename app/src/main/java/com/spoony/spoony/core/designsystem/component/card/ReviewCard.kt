package com.spoony.spoony.core.designsystem.component.card

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
fun ReviewCard(
    reviewId: Int,
    review: String,
    textColor: Color,
    backgroundColor: Color,
    username: String,
    userRegion: String,
    iconUrl: String,
    tagText: String,
    date: String,
    onMenuItemClick: (String) -> Unit,
    menuItems: ImmutableList<String>,
    modifier: Modifier = Modifier,
    addMapCount: Int? = 0,
    onClick: (Int) -> Unit = {},
    imageList: ImmutableList<String> = persistentListOf()
) {
    Column(
        modifier = modifier
            .noRippleClickable { onClick(reviewId) }
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
                text = tagText,
                iconUrl = iconUrl,
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
                text = username,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$userRegion 스푼",
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
        ImageGrid(imageList = imageList)

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
                text = date,
                style = SpoonyAndroidTheme.typography.caption2m,
                color = SpoonyAndroidTheme.colors.gray400
            )
        }
    }
}

@Composable
private fun ImageGrid(imageList: ImmutableList<String>) {
    if (imageList.isEmpty()) return
    val imageRadius = when (imageList.size) {
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
                shape = RoundedCornerShape(imageRadius),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
    Spacer(modifier = Modifier.height(18.dp))
}

private class ReviewImageCountProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(0, 1, 2, 3)
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardPreview(
    @PreviewParameter(ReviewImageCountProvider::class) imageCount: Int
) {
    val imageList = when (imageCount) {
        0 -> persistentListOf()
        else -> persistentListOf<String>().builder().apply {
            repeat(imageCount) {
                add("https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4")
            }
        }.build()
    }

    val suffix = when (imageCount) {
        0 -> "이미지 없음"
        1 -> "이미지 1개"
        2 -> "이미지 2개"
        else -> "이미지 3개"
    }

    SpoonyAndroidTheme {
        ReviewCard(
            reviewId = 1,
            review = "이 식당은 정말 맛있어요! 특히 파스타와 샐러드가 맛있고, 직원들도 친절해요. ($suffix)",
            textColor = SpoonyAndroidTheme.colors.main500,
            backgroundColor = SpoonyAndroidTheme.colors.main100,
            onMenuItemClick = {},
            iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c",
            tagText = "양식",
            date = "2023.10.15",
            username = "스푼이",
            userRegion = "서울 성북구",
            addMapCount = 5,
            imageList = imageList,
            modifier = Modifier.fillMaxWidth(),
            menuItems = persistentListOf("수정하기", "삭제하기")
        )
    }
}
