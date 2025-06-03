package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.presentation.explore.ExploreType

@Composable
fun ExploreEmptyScreen(
    onClick: () -> Unit,
    exploreType: ExploreType,
    modifier: Modifier = Modifier
) {
    val contentDescription = when (exploreType) {
        ExploreType.ALL -> "아직 발견된 장소가 없어요.\n나만의 리스트를 공유해 볼까요?"
        ExploreType.FOLLOWING -> "아직 팔로우 한 유저가 없어요.\n관심 있는 유저들을 팔로우해보세요."
    }
    val buttonText = when (exploreType) {
        ExploreType.ALL -> "등록하러 가기"
        ExploreType.FOLLOWING -> "검색하러 가기"
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 56.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_empty_explore),
            modifier = Modifier
                .size(220.dp)
                .aspectRatio(1f),
            contentDescription = null
        )

        Text(
            text = contentDescription,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    top = 30.dp,
                    bottom = 18.dp
                )
        )

        SpoonyButton(
            text = buttonText,
            size = ButtonSize.Xsmall,
            style = ButtonStyle.Secondary,
            onClick = onClick
        )
    }
}
