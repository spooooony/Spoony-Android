package com.spoony.spoony.core.designsystem.component.slider

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.util.extension.spoonyGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonySlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thumbIcon: Int = R.drawable.ic_slyder_thumb,
    iconColor: Color = Color.Unspecified,
    activeTrackColor: Color = SpoonyAndroidTheme.colors.main400,
    inactiveTrackColor: Color = SpoonyAndroidTheme.colors.gray200,
    trackHeight: Dp = 11.dp,
    hapticEnabled: Boolean = true,
    hapticStep: Float = 0.1f
) {
    val defaultBrush = remember {
        Brush.horizontalGradient(
            listOf(Color.White, activeTrackColor)
        )
    }

    var previousValue by remember { mutableStateOf(value) }
    val view = LocalView.current

    Slider(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)

            if (hapticEnabled && hapticStep > 0) {
                val currentStep = (newValue / hapticStep).toInt()
                val previousStep = (previousValue / hapticStep).toInt()
                if (currentStep != previousStep) view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
            }
            previousValue = newValue
        },
        modifier = modifier,
        enabled = enabled,
        valueRange = 0f..1f,
        thumb = {
            Icon(
                imageVector = ImageVector.vectorResource(thumbIcon),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
            )
        },
        track = { sliderState ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .background(
                        color = inactiveTrackColor,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(sliderState.value)
                        .height(trackHeight)
                        .background(
                            brush = defaultBrush,
                            shape = CircleShape
                        )
                )
            }
        }
    )
}
