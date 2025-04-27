package com.spoony.spoony.presentation.setting.block.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.gray100
import com.spoony.spoony.core.designsystem.theme.gray500
import com.spoony.spoony.core.designsystem.theme.main200
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.spoonyGradient

private enum class BlockButtonState(
    val text: String,
    val textColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val useGradient: Boolean
) {
    BLOCK(
        text = "차단",
        textColor = white,
        backgroundColor = Color.Transparent,
        borderColor = main200,
        useGradient = true
    ),
    UNBLOCK(
        text = "해제",
        textColor = gray500,
        backgroundColor = gray0,
        borderColor = gray100,
        useGradient = false
    )
}

@Composable
fun BlockButton(
    isBlocking: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,

    isSmall: Boolean = true
) {
    val buttonState = remember(isBlocking) {
        if (isBlocking) BlockButtonState.UNBLOCK else BlockButtonState.BLOCK
    }

    val cornerRadius = 12.dp
    val cornerShape = RoundedCornerShape(cornerRadius)

    val buttonModifier = modifier
        .border(
            width = 1.dp,
            color = buttonState.borderColor,
            shape = cornerShape
        )
        .then(
            if (buttonState.useGradient) {
                Modifier.spoonyGradient(
                    cornerRadius = cornerRadius,
                    mainColor = main400,
                    secondColor = white
                )
            } else {
                Modifier
            }
        )
        .noRippleClickable(onClick = onClick)

    Surface(
        shape = cornerShape,
        color = buttonState.backgroundColor,
        modifier = buttonModifier
    ) {
        Text(
            text = buttonState.text,
            style = SpoonyAndroidTheme.typography.body2sb,
            color = buttonState.textColor,
            modifier = Modifier.padding(
                horizontal = if (isSmall) 14.dp else 24.dp,
                vertical = 6.dp
            )
        )
    }
}

@Preview
@Composable
private fun BlockButtonPreview() {
    var isBlocking by remember { mutableStateOf(false) }

    SpoonyAndroidTheme {
        Column {
            BlockButton(
                isBlocking = isBlocking,
                onClick = {
                    isBlocking = !isBlocking
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            BlockButton(
                isBlocking = !isBlocking,
                onClick = {
                    isBlocking = !isBlocking
                }
            )
        }
    }
}
