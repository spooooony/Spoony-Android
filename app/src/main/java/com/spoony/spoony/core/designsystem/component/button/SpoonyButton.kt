package com.spoony.spoony.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun SpoonyButton(
    text: String,
    size: ButtonSize,
    style: ButtonStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val spoonyColors = SpoonyAndroidTheme.colors
    val backgroundColor = remember(enabled, isPressed, style) {
        when {
            !enabled -> when (style) {
                ButtonStyle.Primary -> spoonyColors.main100
                ButtonStyle.Secondary -> spoonyColors.gray300
                ButtonStyle.Tertiary -> spoonyColors.gray100
            }
            isPressed -> when (style) {
                ButtonStyle.Primary -> spoonyColors.main500
                ButtonStyle.Secondary -> spoonyColors.gray800
                ButtonStyle.Tertiary -> spoonyColors.gray100
            }
            else -> when (style) {
                ButtonStyle.Primary -> spoonyColors.main400
                ButtonStyle.Secondary -> spoonyColors.black
                ButtonStyle.Tertiary -> spoonyColors.gray0
            }
        }
    }
    val paddingValues = remember(size) {
        when (size) {
            ButtonSize.Xlarge, ButtonSize.Large, ButtonSize.Medium, ButtonSize.Small -> PaddingValues(vertical = 18.dp)
            ButtonSize.Xsmall -> PaddingValues(vertical = 12.dp)
        }
    }

    val textColor = remember(style) {
        when (style) {
            ButtonStyle.Tertiary -> when {
                !enabled -> spoonyColors.gray400
                else -> spoonyColors.gray600
            }
            else -> spoonyColors.white
        }
    }

    val cornerRadius = remember(size) {
        when (size) {
            ButtonSize.Xlarge -> 8.dp
            ButtonSize.Large, ButtonSize.Medium, ButtonSize.Small, ButtonSize.Xsmall -> 10.dp
        }
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(color = backgroundColor)
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Text(
                text = text,
                color = textColor,
                style = SpoonyAndroidTheme.typography.body2b
            )
        }
    }
}

private class ButtonStyleProvider : PreviewParameterProvider<ButtonStyle> {
    override val values: Sequence<ButtonStyle> = sequenceOf(
        ButtonStyle.Primary,
        ButtonStyle.Secondary,
        ButtonStyle.Tertiary
    )
}

@Preview
@Composable
fun SpoonyButtonEnabledPreview(
    @PreviewParameter(ButtonStyleProvider::class) style: ButtonStyle
) {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ButtonSize.entries.forEach { size ->
                SpoonyButton(
                    text = "버튼",
                    style = style,
                    size = size,
                    onClick = { }
                )
            }
        }
    }
}

@Preview
@Composable
fun SpoonyButtonEnabledIconPreview(
    @PreviewParameter(ButtonStyleProvider::class) style: ButtonStyle
) {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ButtonSize.entries.forEach { size ->
                SpoonyButton(
                    text = "버튼",
                    style = style,
                    size = size,
                    onClick = { },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            modifier = Modifier.size(32.dp),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SpoonyButtonDisabledPreview(
    @PreviewParameter(ButtonStyleProvider::class) style: ButtonStyle
) {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ButtonSize.entries.forEach { size ->
                SpoonyButton(
                    text = "버튼",
                    style = style,
                    size = size,
                    enabled = false,
                    onClick = { }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonWidthModifierPreview() {
    SpoonyAndroidTheme {
        SpoonyButton(
            text = "떠먹으러 가기",
            onClick = {},
            style = ButtonStyle.Secondary,
            size = ButtonSize.Xsmall,
            modifier = Modifier.width(134.dp)
        )
    }
}

@Preview
@Composable
private fun SpoonyButtonTwoButtonPreview() {
    SpoonyAndroidTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SpoonyButton(
                    text = "버튼",
                    onClick = {},
                    style = ButtonStyle.Tertiary,
                    size = ButtonSize.Xsmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(13.dp))
                SpoonyButton(
                    text = "버튼",
                    onClick = {},
                    style = ButtonStyle.Secondary,
                    size = ButtonSize.Xsmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SpoonyButton(
                    text = "버튼",
                    onClick = {},
                    style = ButtonStyle.Tertiary,
                    size = ButtonSize.Xsmall,
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(13.dp))
                SpoonyButton(
                    text = "버튼",
                    onClick = {},
                    style = ButtonStyle.Secondary,
                    size = ButtonSize.Xsmall,
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
