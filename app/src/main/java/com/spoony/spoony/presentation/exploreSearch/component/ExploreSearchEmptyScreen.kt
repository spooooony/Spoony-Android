package com.spoony.spoony.presentation.exploreSearch.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.exploreSearch.type.SearchType

@Composable
fun ExploreSearchEmptyScreen(
    searchType: SearchType = SearchType.USER,
    modifier: Modifier = Modifier
) {
    val text = remember(searchType) {
        if (searchType == SearchType.USER) "닉네임을" else "키워드를"
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 76.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_empty_explore),
            modifier = Modifier
                .size(160.dp),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "검색결과가 없어요",
            style = SpoonyAndroidTheme.typography.body2sb,
            color = SpoonyAndroidTheme.colors.black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "정확한 $text 입력해 보세요",
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center
        )
    }
}
