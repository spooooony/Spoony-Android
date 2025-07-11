package com.spoony.spoony.presentation.gourmet.map.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun MapEmptyBottomSheetContent(
    onClick: () -> Unit,
    description: String = "아직 추가된 장소가 없어요.\n다른 사람의 리스트를 떠먹어보세요!",
    buttonText: String = "떠먹으러 가기",
    buttonStyle: ButtonStyle = ButtonStyle.Primary,
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
            text = description,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
        SpoonyButton(
            text = buttonText,
            size = ButtonSize.Xsmall,
            style = buttonStyle,
            onClick = onClick
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showBackground = true)
private fun MapEmptyBottomSheetContentPreview() {
    SpoonyAndroidTheme {
        MapEmptyBottomSheetContent(
            onClick = {}
        )
    }
}
