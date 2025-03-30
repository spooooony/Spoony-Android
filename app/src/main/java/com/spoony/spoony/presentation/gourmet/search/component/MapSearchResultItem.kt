package com.spoony.spoony.presentation.gourmet.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun MapSearchResultItem(
    placeName: String,
    address: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 14.dp)

    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_pin_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray900,
            modifier = Modifier
                .padding(end = 6.dp)
        )
        Column {
            Text(
                text = placeName,
                style = SpoonyAndroidTheme.typography.body1b,
                color = SpoonyAndroidTheme.colors.gray700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = address,
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray500,
                modifier = Modifier
                    .padding(top = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
