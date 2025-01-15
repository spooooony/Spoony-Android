package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.type.AdvancedSheetState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetScaffoldState
import io.morfly.compose.bottomsheet.material3.rememberBottomSheetState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SpoonyFlexibleBottomSheet(
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    minHeight: Dp = 50.dp,
    dragHandle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val halfScreenHeight = screenHeight / 2

    val sheetState = rememberBottomSheetState(
        initialValue = AdvancedSheetState.Hidden,
        defineValues = {
            AdvancedSheetState.Hidden at height(minHeight)
            AdvancedSheetState.PartiallyExpanded at height(halfScreenHeight)
            AdvancedSheetState.Expanded at height(screenHeight)
        }
    )

    SpoonyAdvancedBottomSheet(
        sheetState = rememberBottomSheetScaffoldState(sheetState),
        modifier = modifier,
        dragHandle = dragHandle,
        sheetContent = sheetContent
    ) {
        content()
    }
}
