package com.spoony.spoony.presentation.auth.onboarding.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.topappbar.SpoonyBasicTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun OnboardingTopAppBar(
    onBackButtonClick: () -> Unit,
    onSkipButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SpoonyBasicTopAppBar(
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
        actions = {
            Text(
                text = "건너뛰기",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.gray500,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .noRippleClickable(onSkipButtonClick)
            )
        },
        modifier = modifier
    )
}
