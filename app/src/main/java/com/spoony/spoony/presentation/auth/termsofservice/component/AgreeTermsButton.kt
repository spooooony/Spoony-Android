package com.spoony.spoony.presentation.auth.termsofservice.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgreeTermsButton(
    title: String,
    onCheckBoxClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onTitleClick: () -> Unit = {},
    isRequired: Boolean = false,
    isChecked: Boolean = false,
    isUnderlined: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .noRippleClickable(onClick = onTitleClick)
        ) {
            Text(
                text = title,
                style = SpoonyAndroidTheme.typography.body1sb,
                color = SpoonyAndroidTheme.colors.gray900,
                textDecoration = if (isUnderlined) TextDecoration.Underline else null
            )

            Text(
                text = if (isRequired) " (필수)" else " (선택)",
                style = SpoonyAndroidTheme.typography.body1sb,
                color = SpoonyAndroidTheme.colors.gray900
            )
        }

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckBoxClick,
                colors = CheckboxColors(
                    checkedCheckmarkColor = SpoonyAndroidTheme.colors.white,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = SpoonyAndroidTheme.colors.main400,
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = SpoonyAndroidTheme.colors.main400,
                    uncheckedBorderColor = SpoonyAndroidTheme.colors.gray400,
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                ),
                modifier = Modifier
                    .size(26.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgreeTermsButtonPreview() {
    SpoonyAndroidTheme {
        var isChecked by remember { mutableStateOf(false) }

        Column {
            AgreeTermsButton(
                title = "만 14세 이상입니다.",
                isUnderlined = false,
                isChecked = isChecked,
                onCheckBoxClick = { isChecked = it }
            )

            AgreeTermsButton(
                title = "스푸니 서비스 이용약관",
                isUnderlined = true,
                isChecked = isChecked,
                onCheckBoxClick = { isChecked = it }
            )
        }
    }
}
