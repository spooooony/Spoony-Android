package com.spoony.spoony.presentation.map.search.component

import androidx.compose.foundation.layout.Row
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
fun MapSearchRecentItem(
    searchText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = searchText,
            style = SpoonyAndroidTheme.typography.body1b,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .weight(1f)
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray400,
            modifier = Modifier
                .padding(vertical = 14.dp)
                .noRippleClickable(onClick = onClick)
        )
    }
}
