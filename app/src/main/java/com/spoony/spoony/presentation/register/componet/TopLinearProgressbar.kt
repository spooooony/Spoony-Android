package com.spoony.spoony.presentation.register.componet

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun TopLinearProgressBar(
    currentStep: Int
) {
    val progress = remember { Animatable(0f) }
    val targetProgress = currentStep.toFloat() / 3

    LaunchedEffect(currentStep) {
        progress.animateTo(
            targetValue = targetProgress,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        )
    }

    LinearProgressIndicator(
        progress = { progress.value },
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .fillMaxWidth()
            .height(4.dp),
        color = SpoonyAndroidTheme.colors.gray100,
        trackColor = SpoonyAndroidTheme.colors.main400,
        strokeCap = StrokeCap.Round
    )
}

@Preview
@Composable
private fun TopLinearProgressBarPreview() {
    SpoonyAndroidTheme {
        var currentStep by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TopLinearProgressBar(
                currentStep = currentStep
            )

            Button(onClick = {
                if (currentStep < 3) {
                    currentStep++
                } else if (currentStep == 3) {
                    currentStep = 0
                }
            }) {
                Text("다음 단계")
            }
        }
    }
}
