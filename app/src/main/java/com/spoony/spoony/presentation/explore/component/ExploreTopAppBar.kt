package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray700
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun ExploreTopAppBar(
    count: Int,
    onClick: () -> Unit,
    place: String = "마포구"
) {
    TagTopAppBar(
        count = count
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(start = 20.dp)
                .noRippleClickable(onClick = onClick)
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "서울특별시 $place",
                style = SpoonyAndroidTheme.typography.title2sb,
                color = SpoonyAndroidTheme.colors.black
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_24),
                contentDescription = null,
                tint = gray700,
                modifier = Modifier
                    .size(26.dp)
                    .padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth())
    }
}
