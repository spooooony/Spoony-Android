package com.spoony.spoony.core.designsystem.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.spoony.spoony.core.util.extension.hexToColor

@Composable
fun IconTag(
    text: String,
    backgroundColorHex: String,
    textColorHex: String,
    iconUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(color = Color.hexToColor(backgroundColorHex))
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
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
            color = Color.hexToColor(textColorHex),
            style = SpoonyAndroidTheme.typography.caption1m
        )
    }
}

@Preview
@Composable
private fun IconTagPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconTag(
                text = "chip",
                backgroundColorHex = "FFCEC6",
                textColorHex = "FF5235",
                iconUrl = "https://github.com/user-attachments/assets/67b8de6f-d4e8-4123-bd7d-93623e41ea8c"
            )
            IconTag(
                text = "chip",
                backgroundColorHex = "FFE1D0",
                textColorHex = "FF7E35",
                iconUrl = "https://github.com/user-attachments/assets/6446572e-80ed-4f1a-962f-f4e4034f49a8"
            )
            IconTag(
                text = "chip",
                backgroundColorHex = "FFE4E5",
                textColorHex = "FF7E84",
                iconUrl = "https://github.com/user-attachments/assets/7442ba9c-2fd6-482d-be95-c140ce78997a"
            )
            IconTag(
                text = "chip",
                backgroundColorHex = "D3F4EB",
                textColorHex = "00CB92",
                iconUrl = "https://github.com/user-attachments/assets/15c95d18-f3e7-4040-93c2-522e61820935"
            )
            IconTag(
                text = "chip",
                backgroundColorHex = "DCE5FE",
                textColorHex = "6A92FF",
                iconUrl = "https://github.com/user-attachments/assets/ab4fe966-c8ec-43f3-b30a-02c5996b8eb1"
            )
            IconTag(
                text = "chip",
                backgroundColorHex = "EEE3FD",
                textColorHex = "AD75F9",
                iconUrl = "https://github.com/user-attachments/assets/18469f99-9570-40d3-a3c0-c6a6bb1c426f"
            )
        }
    }
}
