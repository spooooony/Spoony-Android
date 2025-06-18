package com.spoony.spoony.core.designsystem.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun EmptyContent(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.img_empty_home),
            modifier = Modifier.size(100.dp),
            contentDescription = null
        )
        Text(
            text = text,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
    }
}

@Preview
@Composable
private fun EmptyContentPreview() {
    SpoonyAndroidTheme {
        EmptyContent(
            text = "아직 등록된 장소가 없어요.\n나만의 리스트를 공유해 볼까요?"
        )
    }
}
