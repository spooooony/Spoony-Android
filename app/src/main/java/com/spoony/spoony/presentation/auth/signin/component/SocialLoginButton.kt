package com.spoony.spoony.presentation.auth.signin.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun SocialLoginButton(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )

        Text(
            text = title,
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.gray900,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
