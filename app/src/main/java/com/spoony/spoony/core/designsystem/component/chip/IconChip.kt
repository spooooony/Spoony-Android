package com.spoony.spoony.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun IconChip(
    text: String,
    selectedTextColor: Color,
    unSelectedTextColor: Color,
    selectedIconUrl: String,
    unSelectedIconUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val iconUrl = if (isSelected) selectedIconUrl else unSelectedIconUrl

    val textColor = if (isSelected) selectedTextColor else unSelectedTextColor

    Row(
        modifier = Modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = SpoonyAndroidTheme.colors.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                modifier
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier.size(16.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = textColor,
            style = SpoonyAndroidTheme.typography.body2sb
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IconChipPreview() {
    var isSelected by remember { mutableStateOf(false) }
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconChip(
                text = "chip",
                selectedTextColor = Color.Black,
                unSelectedTextColor = Color.White,
                modifier = Modifier.then(
                    if (isSelected) {
                        Modifier.background(Color.White)
                    } else {
                        Modifier.background(Color.Black)
                    }
                ),
                onClick = {
                    isSelected = !isSelected
                },
                isSelected = isSelected,
                selectedIconUrl = "https://github.com/user-attachments/assets/1e0bc0e5-a1c6-44e8-b6f4-f47aced44441",
                unSelectedIconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8"

            )

            IconChip(
                text = "chip",
                selectedTextColor = Color.White,
                unSelectedTextColor = SpoonyAndroidTheme.colors.gray600,
                modifier = Modifier.then(
                    if (isSelected) {
                        Modifier.background(Color.Black)
                    } else {
                        Modifier.background(SpoonyAndroidTheme.colors.gray0)
                    }
                ),
                onClick = {
                    isSelected = !isSelected
                },
                isSelected = isSelected,
                selectedIconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8",
                unSelectedIconUrl = "https://github.com/user-attachments/assets/1e0bc0e5-a1c6-44e8-b6f4-f47aced44441"
            )

            IconChip(
                text = "chip",
                selectedTextColor = Color.White,
                unSelectedTextColor = Color.White,
                onClick = {
                    isSelected = !isSelected
                },
                modifier = Modifier.then(
                    if (isSelected) {
                        Modifier.background(Color.Black)
                    } else {
                        Modifier.background(SpoonyAndroidTheme.colors.main400)
                    }
                ),
                isSelected = isSelected,
                selectedIconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8",
                unSelectedIconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8"
            )
        }
    }
}
