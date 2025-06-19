package com.spoony.spoony.presentation.gourmet.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.hexToColor
import com.spoony.spoony.presentation.gourmet.map.model.ReviewCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MapCardPager(
    placeCardList: ImmutableList<ReviewCardModel>,
    onPlaceCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { placeCardList.size }
    )
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState.currentPage) {
        currentIndex = pagerState.currentPage
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            contentPadding = PaddingValues(horizontal = 26.dp),
            pageSpacing = 26.dp
        ) { currentPage ->
            with(placeCardList[currentPage]) {
                MapPlaceDetailCard(
                    placeName = placeName,
                    review = description,
                    imageUrlList = photoUrl.take(3).toImmutableList(),
                    categoryIconUrl = categoryInfo.iconUrl,
                    categoryName = categoryInfo.categoryName,
                    textColor = Color.hexToColor(categoryInfo.textColor),
                    backgroundColor = Color.hexToColor(categoryInfo.backgroundColor),
                    onClick = { onPlaceCardClick(reviewId) },
                    username = this.userName,
                    placeSpoon = userRegion,
                    addMapCount = addMapCount
                )
            }
        }

        if (placeCardList.size > 1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(30.dp)
                    .background(
                        color = SpoonyAndroidTheme.colors.gray200,
                        shape = CircleShape
                    )
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                repeat(placeCardList.size) { index ->
                    val isSelected = index == currentIndex

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(8.dp)
                            .background(if (isSelected) SpoonyAndroidTheme.colors.black else SpoonyAndroidTheme.colors.gray500)
                    )
                }
            }
        } else {
            Spacer(modifier.height(30.dp))
        }
    }
}
