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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.component.tag.IconTag
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.presentation.explore.type.ExploreDropdownOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ExploreItem(
    review: String,
    textColor: Color,
    backgroundColor: Color,
    onMenuItemClick: (ExploreDropdownOption) -> Unit,
    modifier: Modifier = Modifier,
    iconUrl: String? = "",
    tagText: String? = "",
    date: String? = "",
    username: String?,
    placeSpoon: String?,
    addMapCount: Int? = 0,
    imageList: ImmutableList<String>? = persistentListOf(),
    menuItems: ImmutableList<ExploreDropdownOption> = persistentListOf(ExploreDropdownOption.REPORT)
) {
    Column(
        modifier = modifier
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
            IconDropdownMenu(
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

class ImageCountProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(0, 1, 2, 3, 4)
    override val count: Int get() = values.count()
}

@Preview(showBackground = true)
@Composable
fun PreviewExploreItem(@PreviewParameter(ImageCountProvider::class) imageCount: Int) {
    val menuItems = persistentListOf(ExploreDropdownOption.REPORT)

    val imageList = persistentListOf(
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/475019001_17850129315389564_1569540604557943007_n.jpg?stp=dst-jpg_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6IkNBUk9VU0VMX0lURU0uaW1hZ2VfdXJsZ2VuLjEyMDB4MTUwMC5zZHIuZjc1NzYxLmRlZmF1bHRfaW1hZ2UifQ&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_oc=Q6cZ2QEeJaNgrVcYYz_qbLQQ75KFwCaVRf-gG2dloaU2n7fYAVcfCbiBGlF0HmO0KB2B-nM&_nc_ohc=NvtPxJL5iMoQ7kNvwFbs0nm&_nc_gid=KbyEVcCrnElGGFJ9jS9JKw&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU1MzE4OTE1Njk0MTE1MDUzOA%3D%3D.3-ccb7-5&oh=00_AfFyf3zIsjGYahZpFwS0WTtOdSeUjjFj23jiaot6Ydbgug&oe=67FA5D0F&_nc_sid=7a9f4b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/475083398_17850128934389564_6415536887386537093_n.jpg?stp=dst-jpg_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6IkNBUk9VU0VMX0lURU0uaW1hZ2VfdXJsZ2VuLjEyMDB4MTUwMC5zZHIuZjc1NzYxLmRlZmF1bHRfaW1hZ2UifQ&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_oc=Q6cZ2QEeJaNgrVcYYz_qbLQQ75KFwCaVRf-gG2dloaU2n7fYAVcfCbiBGlF0HmO0KB2B-nM&_nc_ohc=Szk3zsbpqMsQ7kNvwHtO4rP&_nc_gid=KbyEVcCrnElGGFJ9jS9JKw&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU1MzE4Nzg5MTYzNjk5NTU5Nw%3D%3D.3-ccb7-5&oh=00_AfGgCfe6_9kAADNzOsrpLSN4TwkVgegKrnEfIyzrfzgwOg&oe=67FA8662&_nc_sid=7a9f4b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/475027153_17850128172389564_3642654276052745389_n.jpg?stp=dst-jpg_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6IkNBUk9VU0VMX0lURU0uaW1hZ2VfdXJsZ2VuLjEyMDB4MTUwMC5zZHIuZjc1NzYxLmRlZmF1bHRfaW1hZ2UifQ&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_oc=Q6cZ2QEeJaNgrVcYYz_qbLQQ75KFwCaVRf-gG2dloaU2n7fYAVcfCbiBGlF0HmO0KB2B-nM&_nc_ohc=vZU6plhYL5gQ7kNvwHP_5iA&_nc_gid=KbyEVcCrnElGGFJ9jS9JKw&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU1MzE4NTYzMzkzNTQzOTc3Nw%3D%3D.3-ccb7-5&oh=00_AfFO-0uu92QzRPrvJeMdiQiPFzYs1_TSUw1tpQm7XwBqcg&oe=67FA7182&_nc_sid=7a9f4b",
        "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/475019001_17850129315389564_1569540604557943007_n.jpg?stp=dst-jpg_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6IkNBUk9VU0VMX0lURU0uaW1hZ2VfdXJsZ2VuLjEyMDB4MTUwMC5zZHIuZjc1NzYxLmRlZmF1bHRfaW1hZ2UifQ&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_oc=Q6cZ2QEeJaNgrVcYYz_qbLQQ75KFwCaVRf-gG2dloaU2n7fYAVcfCbiBGlF0HmO0KB2B-nM&_nc_ohc=NvtPxJL5iMoQ7kNvwFbs0nm&_nc_gid=KbyEVcCrnElGGFJ9jS9JKw&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU1MzE4OTE1Njk0MTE1MDUzOA%3D%3D.3-ccb7-5&oh=00_AfFyf3zIsjGYahZpFwS0WTtOdSeUjjFj23jiaot6Ydbgug&oe=67FA5D0F&_nc_sid=7a9f4b"
    ).take(imageCount).toPersistentList()

    SpoonyAndroidTheme {
        ExploreItem(
            username = "gambasgirl",
            placeSpoon = "성북구",
            review = "이자카야인데 친구랑 가서 안주만 5개 넘게 시킴.. 명성이 자자한 고등어봉 초밥은 꼭 시키세요! 입에 넣자마자 사르르 녹아요.",
            addMapCount = imageCount * 5,
            tagText = "카페",
            date = "약 5시간 전",
            imageList = imageList,
            backgroundColor = Color.hexToColor("FFCEC6"),
            textColor = Color.hexToColor("FF5235"),
            iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c",
            menuItems = menuItems,
            onMenuItemClick = { option ->
                when (option) {
                    ExploreDropdownOption.REPORT -> {
                        // Handle report option
                    }
                }
            },
            modifier = Modifier
        )
    }
}
