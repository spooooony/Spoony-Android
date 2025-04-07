package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun TitleTopAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white
) {
    SpoonyBasicTopAppBar(
        content = {
            Text(
                text = title,
                style = SpoonyAndroidTheme.typography.title2b,
                color = SpoonyAndroidTheme.colors.black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 44.dp),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(32.dp)
                    .noRippleClickable(onBackButtonClick)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray700
                )
            }
        },
        modifier = modifier,
        backgroundColor = backgroundColor
    )
}

@Preview
@Composable
private fun TitleTopAppBarPreview() {
    SpoonyAndroidTheme {
        TitleTopAppBar(
            title = "신고하기",
            onBackButtonClick = {}
        )
    }
}
