package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.dropShadow
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.model.FilterOption
import com.spoony.spoony.presentation.explore.model.FilterType
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FilterChipRow(
    chipItems: ImmutableList<FilterOption>,
    onSortFilterClick: () -> Unit,
    onFilterClick: (FilterType) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
) {
    val fontScale = LocalDensity.current.fontScale
    val density = LocalDensity.current
    var lazyRowHeight by remember { mutableStateOf(0.dp) }
    var sortFilterWidth by remember { mutableStateOf(0.dp) }
    val rightOverflowPadding = sortFilterWidth / 2
    val rightMargin = 20.dp
    val lazyRowEndPadding = rightOverflowPadding + 5.dp
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = rightMargin + rightOverflowPadding)
            .wrapContentHeight()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    with(density) {
                        lazyRowHeight = coordinates.size.height.toDp()
                    }
                },
            contentPadding = PaddingValues(
                start = 20.dp,
                end = lazyRowEndPadding
            ),
            horizontalArrangement = horizontalArrangement
        ) {
            items(chipItems, key = { it.sort }) { chipItem ->
                FilterChip(
                    text = chipItem.text,
                    isSelected = chipItem.isSelected,
                    onClick = { onFilterClick(chipItem.sort) },
                    leftIcon = with(chipItem) {
                        if (leftIconResId != null) {
                            {
                                Icon(
                                    imageVector = ImageVector.vectorResource(leftIconResId),
                                    contentDescription = null,
                                    tint = if (isSelected) {
                                        SpoonyAndroidTheme.colors.main400
                                    } else {
                                        SpoonyAndroidTheme.colors.gray400
                                    },
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else {
                            null
                        }
                    },
                    rightIcon = with(chipItem) {
                        if (rightIconResId != null) {
                            {
                                Icon(
                                    imageVector = ImageVector.vectorResource(rightIconResId),
                                    contentDescription = null,
                                    tint = if (isSelected) {
                                        SpoonyAndroidTheme.colors.main400
                                    } else {
                                        SpoonyAndroidTheme.colors.gray400
                                    },
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else {
                            null
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onGloballyPositioned { coordinates ->
                    with(density) {
                        sortFilterWidth = coordinates.size.width.toDp()
                    }
                }
                .offset { IntOffset((sortFilterWidth / 2).roundToPx(), 0.dp.roundToPx()) }
                .height(if (lazyRowHeight > 0.dp) lazyRowHeight else 36.dp)
                .dropShadow(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF82848D).copy(alpha = 0.3f),
                    offsetX = (-8).dp,
                    offsetY = 0.dp,
                    blur = 10.dp,
                    spread = 0.dp
                )
                .noRippleClickable(onSortFilterClick)
                .background(
                    color = SpoonyAndroidTheme.colors.gray0,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = SpoonyAndroidTheme.colors.gray100,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_sort_filter_16),
                modifier = Modifier.size(16.dp * fontScale),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}
