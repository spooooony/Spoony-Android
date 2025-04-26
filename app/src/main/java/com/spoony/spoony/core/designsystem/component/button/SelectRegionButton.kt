package com.spoony.spoony.core.designsystem.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun SelectRegionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    region: String = "서울 마포구",
    isSelected: Boolean = false
) {
    val iconColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray400
    val textColor = if (isSelected) SpoonyAndroidTheme.colors.black else SpoonyAndroidTheme.colors.gray500

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "나는",
            style = SpoonyAndroidTheme.typography.body1m,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .padding(end = 22.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .border(
                    color = SpoonyAndroidTheme.colors.gray100,
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .noRippleClickable(onClick = onClick)
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_spoon_24),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = region,
                style = SpoonyAndroidTheme.typography.body2m,
                color = textColor
            )
        }

        Text(
            text = "스푼",
            style = SpoonyAndroidTheme.typography.body1m,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .padding(start = 22.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectRegionButtonPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            SelectRegionButton(
                onClick = {}
            )

            SelectRegionButton(
                onClick = {},
                region = "경기도 의왕시",
                isSelected = true,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}
