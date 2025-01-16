package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import io.morfly.compose.bottomsheet.material3.BottomSheetState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpoonyFlexibleBottomSheet(
    sheetState: BottomSheetState<AdvancedSheetState>,
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dragHandle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    SpoonyAdvancedBottomSheet(
        sheetState = rememberBottomSheetScaffoldState(sheetState),
        modifier = modifier,
        dragHandle = dragHandle,
        sheetContent = sheetContent
    ) {
        content()
    }
}
