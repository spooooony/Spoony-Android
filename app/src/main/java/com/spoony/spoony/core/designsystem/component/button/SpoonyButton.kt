package com.spoony.spoony.core.designsystem.component.button

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.modifier.noRippleClickable

@Composable
fun SpoonyButton(
    text: String,
    size: ButtonSize = ButtonSize.Medium,
    style: ButtonStyle = ButtonStyle.Primary,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = when {
        !enabled -> when (style) {
            ButtonStyle.Primary -> SpoonyAndroidTheme.colors.main100
            ButtonStyle.Secondary -> SpoonyAndroidTheme.colors.gray300
            ButtonStyle.Tertiary -> SpoonyAndroidTheme.colors.gray100
        }
        isPressed -> when (style) {
            ButtonStyle.Primary -> SpoonyAndroidTheme.colors.main500
            ButtonStyle.Secondary -> SpoonyAndroidTheme.colors.gray800
            ButtonStyle.Tertiary -> SpoonyAndroidTheme.colors.gray100
        }

        else -> when (style) {
            ButtonStyle.Primary -> SpoonyAndroidTheme.colors.main400
            ButtonStyle.Secondary -> SpoonyAndroidTheme.colors.black
            ButtonStyle.Tertiary -> SpoonyAndroidTheme.colors.gray0
        }
    }
    val paddingValues = when (size) {
        ButtonSize.Xlarge -> PaddingValues(horizontal = 16.dp, vertical = 16.5.dp)
        ButtonSize.Large, ButtonSize.Medium, ButtonSize.Small -> PaddingValues(horizontal = 16.dp, vertical = 18.dp)
        ButtonSize.Xsmall -> PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    }

    val textStyle = when (size) {
        ButtonSize.Xlarge -> SpoonyAndroidTheme.typography.body1b
        ButtonSize.Large, ButtonSize.Medium, ButtonSize.Small, ButtonSize.Xsmall -> SpoonyAndroidTheme.typography.body2b
    }

    val textColor = when (style) {
        ButtonStyle.Tertiary -> when {
            !enabled -> SpoonyAndroidTheme.colors.gray400
            else -> SpoonyAndroidTheme.colors.gray600
        }
        else -> SpoonyAndroidTheme.colors.white
    }

    val cornerRadius = when (size) {
        ButtonSize.Xlarge -> 8.dp
        ButtonSize.Large, ButtonSize.Medium, ButtonSize.Small, ButtonSize.Xsmall -> 10.dp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(color = backgroundColor)
            .noRippleClickable(
                interactionSource = interactionSource,
                onClick = onClick
            )
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = textColor,
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonPrimaryEnabledPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Xlarge
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Large
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Medium
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Small
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Xsmall
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonPrimaryDisabledPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Primary,
                size = ButtonSize.Xlarge,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Primary,
                size = ButtonSize.Large,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Primary,
                size = ButtonSize.Medium,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Primary,
                size = ButtonSize.Small,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Primary,
                size = ButtonSize.Xsmall,
                enabled = false
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonPrimaryIconTextPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Xlarge,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Large,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Medium,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Small,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Primary,
                size = ButtonSize.Xsmall,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonSecondaryEnabledPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Large
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Medium
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Small
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xsmall
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonSecondaryDisabledPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Large,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Medium,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Small,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xsmall,
                enabled = false
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonSecondaryIconTextPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Large,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Medium,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Small,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xsmall,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "ic_spoon_button",
                        tint = Color.Unspecified
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonTertiaryEnabledPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Xlarge
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Large
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Medium
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Small
            )
            SpoonyButton(
                text = "버튼",
                onClick = {},
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Xsmall
            )
        }
    }
}

@Preview
@Composable
private fun SpoonyButtonTertiaryDisabledPreview() {
    SpoonyAndroidTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Xlarge,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Large,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Medium,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Small,
                enabled = false
            )
            SpoonyButton(
                text = "버튼",
                style = ButtonStyle.Tertiary,
                size = ButtonSize.Xsmall,
                enabled = false
            )
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
                    style = ButtonStyle.Tertiary,
                    size = ButtonSize.Xsmall,
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(13.dp))
                SpoonyButton(
                    text = "버튼",
                    style = ButtonStyle.Secondary,
                    size = ButtonSize.Xsmall,
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
