package com.spoony.spoony.presentation.profileedit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.black
import com.spoony.spoony.core.designsystem.theme.gray600
import kotlinx.collections.immutable.persistentListOf

private data class TextInfo(
    val text: String,
    val style: TextStyle,
    val color: Color
)

@Composable
fun ProfileImageCard(
    imageUrl: String,
    name: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UrlImage(
            imageUrl = imageUrl,
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            contentScale = ContentScale.Crop
        )
        Column {
            val body1Style = SpoonyAndroidTheme.typography.body1sb
            val body2Style = SpoonyAndroidTheme.typography.body2m

            val textInfoList = remember(name, description, body1Style, body2Style) {
                persistentListOf(
                    TextInfo(name, body1Style, black),
                    TextInfo(description, body2Style, gray600)
                )
            }
            textInfoList.forEach { textInfo ->
                Text(
                    text = textInfo.text,
                    style = textInfo.style,
                    color = textInfo.color,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileCardPreview() {
    SpoonyAndroidTheme {
        ProfileImageCard(
            imageUrl = "",
            name = "Profile Image_1 name",
            description = "Basic Spoon. Welcome to the Spoony!"
        )
    }
}
