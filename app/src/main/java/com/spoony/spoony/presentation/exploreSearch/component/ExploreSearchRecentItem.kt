package com.spoony.spoony.presentation.exploreSearch.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun ExploreSearchRecentItem(
    onItemClick: (String) -> Unit,
    onRemoveRecentSearchItem: (String) -> Unit,
    searchKeyword: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
            .noRippleClickable { onItemClick(searchKeyword) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = searchKeyword,
            modifier = Modifier.weight(1f),
            style = SpoonyAndroidTheme.typography.body1b,
            color = SpoonyAndroidTheme.colors.gray700
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray400,
            modifier = Modifier.noRippleClickable {
                onRemoveRecentSearchItem(searchKeyword)
            }
        )
    }
}
