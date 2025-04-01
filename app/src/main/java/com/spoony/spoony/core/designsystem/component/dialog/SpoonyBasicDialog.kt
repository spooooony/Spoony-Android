package com.spoony.spoony.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun SpoonyBasicDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false)
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .background(
                    color = SpoonyAndroidTheme.colors.white,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    top = 40.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            content()
        }
    }
}
