package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PlaceDetailImageLazyRow(
    imageList: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val imageSize = if (imageList.size == 1) screenWidth * 1f - 40.dp else screenWidth * (278f / 360f)
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        itemsIndexed(imageList, key = { index, _ -> index }) { _, imageUrl ->
            AsyncImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SpoonyAndroidTheme.colors.gray300),
                model = ImageRequest.Builder(context)
                    .crossfade(true)
                    .data(imageUrl)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}
