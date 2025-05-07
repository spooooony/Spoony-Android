package com.spoony.spoony.presentation.exploreSearch.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.exploreSearch.type.SearchType

@Composable
fun ExploreSearchRecentEmptyScreen(
    searchType: SearchType = SearchType.USER,
    modifier: Modifier = Modifier
) {
    val text = remember(searchType) {
        if (searchType == SearchType.USER) "팔로우 하고 싶은 유저" else "원하는 키워드"
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 76.dp)
    ) {
        Box(
            modifier = Modifier
                .width(width = 160.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(SpoonyAndroidTheme.colors.gray800)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "${text}를 검색해 보세요",
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center
        )
    }
}
