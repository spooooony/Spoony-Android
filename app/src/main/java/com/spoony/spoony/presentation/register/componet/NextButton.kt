package com.spoony.spoony.presentation.register.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun NextButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    SpoonyButton(
        text = "다음",
        size = ButtonSize.Xlarge,
        style = ButtonStyle.Primary,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        onClick = onClick
    )
}

@Preview
@Composable
private fun NextButtonPreview() {
    var enabled by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        SpoonyAndroidTheme {
            NextButton(enabled = enabled, onClick = { enabled = !enabled })
        }
    }
}
