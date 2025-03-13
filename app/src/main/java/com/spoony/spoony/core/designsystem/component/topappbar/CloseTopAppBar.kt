package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun CloseTopAppBar(
    title: String,
    backgroundColor: Color = SpoonyAndroidTheme.colors.white,
    onCloseButtonClick: () -> Unit
) {
    SpoonyBasicTopAppBar(
        actions = {
            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .noRippleClickable(onClick = onCloseButtonClick)
                        .padding(end = 12.dp),
                    tint = SpoonyAndroidTheme.colors.gray400
                )
            }
        },
        backgroundColor = backgroundColor
    ) {
        Text(
            text = title,
            style = SpoonyAndroidTheme.typography.title2b,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .padding(start = 20.dp)
        )
    }
}

@Preview
@Composable
private fun CloseTopAppBarPreview() {
    SpoonyAndroidTheme {
        CloseTopAppBar(
            title = "홍대입구역",
            onCloseButtonClick = {}
        )
    }
}
