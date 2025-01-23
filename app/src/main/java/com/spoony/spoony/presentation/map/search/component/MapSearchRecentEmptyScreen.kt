package com.spoony.spoony.presentation.map.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun MapSearchRecentEmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 72.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_map_search_recent_empty),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(220 / 100f)
                .padding(horizontal = 50.dp)
        )
        Text(
            text = "구체적인 장소를 검색해 보세요",
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier
                .padding(top = 24.dp)
        )
    }
}
