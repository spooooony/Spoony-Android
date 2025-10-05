package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.ExploreType
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ExploreHeaderSection(
    tabList: ImmutableList<String>,
    exploreType: ExploreType,
    onChangeTab: (ExploreType) -> Unit,
    onClickSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExploreTabRow(
            onTabChange = onChangeTab,
            tabList = tabList,
            exploreType = exploreType
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_search_20),
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClickSearch),
            contentDescription = "검색",
            tint = Color.Unspecified
        )
    }
}
