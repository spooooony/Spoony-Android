package com.spoony.spoony.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
fun BackAndMenuTopAppBar(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit = {},
    onMenuButtonClick: (() -> Unit)? = null
) {
    SpoonyBasicTopAppBar(
        modifier = modifier,
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
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
        actions = {
            if (onMenuButtonClick != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_kebabmenu_gray500_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray500,
                    modifier = Modifier.noRippleClickable(onClick = onMenuButtonClick)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BackAndMenuTopAppBarPreview() {
    SpoonyAndroidTheme {
        BackAndMenuTopAppBar(
            onBackButtonClick = {},
            onMenuButtonClick = {}
        )
    }
}
