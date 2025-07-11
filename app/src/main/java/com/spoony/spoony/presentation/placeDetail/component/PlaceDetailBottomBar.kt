package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun PlaceDetailBottomBar(
    addMapCount: Int,
    onSearchMapClick: () -> Unit,
    onAddMapButtonClick: () -> Unit,
    onDeletePinMapButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isNotMine: Boolean = true,
    isAddMap: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SpoonyAndroidTheme.colors.white)
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpoonyButton(
            text = "길찾기",
            onClick = onSearchMapClick,
            style = ButtonStyle.Secondary,
            size = ButtonSize.Medium,
            modifier = Modifier.weight(1f)
        )
        if (isNotMine) {
            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .sizeIn(minWidth = 56.dp)
                    .noRippleClickable(
                        if (isAddMap) {
                            onDeletePinMapButtonClick
                        } else {
                            onAddMapButtonClick
                        }
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = if (isAddMap) R.drawable.ic_add_map_main400_24 else R.drawable.ic_add_map_gray400_24),
                    modifier = Modifier
                        .size(32.dp),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = addMapCount.toString(),
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = if (isAddMap) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray800
                )
            }
        }
    }
}
