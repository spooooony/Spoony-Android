package com.spoony.spoony.presentation.follow.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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

@Immutable
private data class ButtonData(
    val text: String,
    val textColor: Color,
    val backgroundColor: Color,
    val needsGradient: Boolean
)

@Composable
fun FollowButton(
    isFollowing: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonData = remember(isFollowing) {
        if (isFollowing) {
            ButtonData(
                text = "팔로잉",
                textColor = gray500,
                backgroundColor = gray0,
                needsGradient = false
            )
        } else {
            ButtonData(
                text = "팔로우",
                textColor = white,
                backgroundColor = Color.Transparent,
                needsGradient = true
            )
        }
    }

    val cornerShape = RoundedCornerShape(12.dp)

    val buttonModifier = if (buttonData.needsGradient) {
        modifier
            .border(
                width = 1.dp,
                color = gray100,
                shape = cornerShape
            )
            .spoonyGradient(
                cornerRadius = 12.dp,
                mainColor = main400,
                secondColor = white
            )
            .noRippleClickable(onClick = onClick)
    } else {
        modifier
            .border(
                width = 1.dp,
                color = gray100,
                shape = cornerShape
            )
            .noRippleClickable(onClick = onClick)
    }

    Surface(
        shape = cornerShape,
        color = buttonData.backgroundColor,
        modifier = buttonModifier
    ) {
        Text(
            text = buttonData.text,
            style = SpoonyAndroidTheme.typography.body2sb,
            color = buttonData.textColor,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
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
