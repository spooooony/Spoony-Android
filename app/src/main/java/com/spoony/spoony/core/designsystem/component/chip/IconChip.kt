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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.spoonyGradient

@Composable
fun IconChip(
    text: String,
    selectedIconUrl: String,
    unSelectedIconUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isGradient: Boolean = false
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = SpoonyAndroidTheme.colors.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                when {
                    isSelected && isGradient -> Modifier.spoonyGradient(12.dp)
                    isSelected -> Modifier.background(color = SpoonyAndroidTheme.colors.main400)
                    else -> Modifier.background(color = SpoonyAndroidTheme.colors.gray0)
                }
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        UrlImage(
            imageUrl = if (isSelected) selectedIconUrl else unSelectedIconUrl,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            color = if (isSelected) SpoonyAndroidTheme.colors.white else SpoonyAndroidTheme.colors.gray600,
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
                onClick = {
                    isSelected = !isSelected
                },
                isGradient = true,
                isSelected = isSelected,
                unSelectedIconUrl = "https://github.com/user-attachments/assets/1e0bc0e5-a1c6-44e8-b6f4-f47aced44441",
                selectedIconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8"
            )

            IconChip(
                text = "chip",
                onClick = {
                    isSelected = !isSelected
                },
                isSelected = isSelected,
                unSelectedIconUrl = "https://github.com/user-attachments/assets/1e0bc0e5-a1c6-44e8-b6f4-f47aced44441",
                selectedIconUrl = "https://github.com/user-attachments/assets/c1752fb8-c771-4931-8a45-3d43a76209f8"
            )
        }
    }
}
