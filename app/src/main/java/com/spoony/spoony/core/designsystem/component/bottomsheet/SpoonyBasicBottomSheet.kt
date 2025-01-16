package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonyBasicBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    isBackgroundDimmed: Boolean = true,
    dragHandle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = SpoonyAndroidTheme.colors.white,
        scrimColor = if (isBackgroundDimmed) BottomSheetDefaults.ScrimColor else Color.Transparent,
        dragHandle = dragHandle
    ) {
        content()
    }
}
