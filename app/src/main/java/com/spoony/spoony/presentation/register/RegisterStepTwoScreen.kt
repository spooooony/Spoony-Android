package com.spoony.spoony.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.register.component.NextButton

@Composable
fun RegisterStepTwoScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "거의 다 왔어요!",
            style = SpoonyAndroidTheme.typography.body1m,
            color = SpoonyAndroidTheme.colors.gray900,
            modifier = Modifier.padding(top = 24.dp)
        )

        // Step 2 content will go here

        Spacer(modifier = Modifier.height(100.dp))

        NextButton(
            enabled = true, // Will be controlled by validation
            onClick = onComplete,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}
