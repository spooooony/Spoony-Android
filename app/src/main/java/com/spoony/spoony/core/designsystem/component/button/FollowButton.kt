package com.spoony.spoony.core.designsystem.component.button

import androidx.compose.foundation.border
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
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.spoonyGradient

private enum class FollowButtonState(
    val text: String,
    val textColor: Color,
    val backgroundColor: Color,
    val useGradient: Boolean
) {
    FOLLOW(
        text = "팔로우",
        textColor = white,
        backgroundColor = Color.Transparent,
        useGradient = true
    ),
    FOLLOWING(
        text = "팔로잉",
        textColor = gray500,
        backgroundColor = gray0,
        useGradient = false
    ),
    UNBLOCK(
        text = "차단 해제",
        textColor = white,
        backgroundColor = Color.Transparent,
        useGradient = true
    )
}

@Composable
fun FollowButton(
    isFollowing: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBlocked: Boolean = false,
    isSmall: Boolean = true
) {
    val buttonState = remember(isFollowing, isBlocked) {
        when {
            isBlocked -> FollowButtonState.UNBLOCK
            isFollowing -> FollowButtonState.FOLLOWING
            else -> FollowButtonState.FOLLOW
        }
    }

    val cornerRadius = 12.dp
    val cornerShape = RoundedCornerShape(cornerRadius)

    val buttonModifier = modifier
        .border(
            width = 1.dp,
            color = gray100,
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
fun FollowButtonPreview() {
    var isFollowing by remember { mutableStateOf(false) }

    SpoonyAndroidTheme {
        FollowButton(
            isFollowing = isFollowing,
            onClick = {
                isFollowing = !isFollowing
            }
        )
    }
}
