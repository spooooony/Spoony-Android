package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import io.morfly.compose.bottomsheet.material3.BottomSheetScaffold
import io.morfly.compose.bottomsheet.material3.BottomSheetScaffoldState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SpoonyAdvancedBottomSheet(
    sheetState: BottomSheetScaffoldState<AdvancedSheetState>,
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dragHandle: @Composable () -> Unit = { SpoonyBasicDragHandle() },
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            sheetContent()
        },
        modifier = modifier,
        sheetContainerColor = SpoonyAndroidTheme.colors.white,
        sheetDragHandle = dragHandle
    ) {
        content()
    }
}
