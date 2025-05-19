package com.spoony.spoony.presentation.auth.onboarding.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun OnBoardingButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    SpoonyButton(
        text = "다음",
        size = ButtonSize.Xlarge,
        style = ButtonStyle.Primary,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        enabled = enabled
    )
}
