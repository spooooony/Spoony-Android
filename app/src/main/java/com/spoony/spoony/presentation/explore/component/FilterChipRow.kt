package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.explore.model.FilterChip
import com.spoony.spoony.presentation.explore.model.FilterChipDataProvider

@Composable
fun FilterChipRow(
    chipItems: List<FilterChip>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement
    ) {
        items(chipItems, key = { it.sort }) { chipItem ->
            FilterChip(
                text = chipItem.text,
                isSelected = chipItem.isSelected,
                onClick = chipItem.onClick,
                leftIcon = {
                    chipItem.leftIconResId?.let {
                        Icon(
                            imageVector = ImageVector.vectorResource(it),
                            contentDescription = null,
                            tint = if (chipItem.isSelected) {
                                SpoonyAndroidTheme.colors.main400
                            } else {
                                SpoonyAndroidTheme.colors.gray400
                            },
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                rightIcon = {
                    chipItem.rightIconResId?.let {
                        Icon(
                            imageVector = ImageVector.vectorResource(it),
                            contentDescription = null,
                            tint = if (chipItem.isSelected) {
                                SpoonyAndroidTheme.colors.main400
                            } else {
                                SpoonyAndroidTheme.colors.gray400
                            },
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun FilterChipRowPreview() {
    val chipItems = FilterChipDataProvider.getDefaultFilterChips()

    SpoonyAndroidTheme {
        FilterChipRow(chipItems = chipItems)
    }
}
