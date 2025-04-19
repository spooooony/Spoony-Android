package com.spoony.spoony.presentation.auth.termsofservice.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgreeAllButton(
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isChecked) {
                    SpoonyAndroidTheme.colors.main0
                } else {
                    SpoonyAndroidTheme.colors.gray0
                },
                shape = RoundedCornerShape(15.dp)
            )
            .padding(15.dp)
    ) {
        Text(
            text = "전체 동의",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.gray900
        )

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onClick,
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
fun AgreeAllButtonPreview() {
    SpoonyAndroidTheme {
        var isChecked by remember { mutableStateOf(false) }
        AgreeAllButton(
            onClick = { isChecked = it },
            isChecked = isChecked
        )
    }
}
