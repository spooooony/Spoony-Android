package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonyBasicBottomSheet(
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    isBackgroundDimmed: Boolean = true,
    dragHandle: @Composable () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = SpoonyAndroidTheme.colors.white,
        scrimColor = if (isBackgroundDimmed) BottomSheetDefaults.ScrimColor else Color.Transparent,
        dragHandle = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 22.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(
                            width = 24.dp,
                            height = Dp.Hairline.plus(2.dp)
                        )
                        .background(SpoonyAndroidTheme.colors.gray300),
                )
                dragHandle()
            }
        }
    ) {
        content()
    }
}
