package com.spoony.spoony.presentation.register.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun RegisterTooltip(
    text: String,
    modifier: Modifier = Modifier,
    arrowPositionFraction: Float = 0.75f
) {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .padding(horizontal = 42.dp)
            .offset(y = offsetY.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, SpoonyAndroidTheme.colors.main400)
                .background(SpoonyAndroidTheme.colors.main400)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_tooltip_spoon_20),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.white
            )
        }

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_register_tooltip_arrow),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.offset(
                    x = (maxWidth * arrowPositionFraction),
                    y = (-5).dp
                )
            )
        }
    }
}

@Preview
@Composable
private fun RegisterTooltipPreview() {
    SpoonyAndroidTheme {
        RegisterTooltip(
            text = "닉네임을 입력해주세요.",
            modifier = Modifier.offset { IntOffset(0, 0) }
        )
    }
}
