package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    dragHandle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            sheetContent()
        },
        modifier = modifier,
        containerColor = SpoonyAndroidTheme.colors.white,
        sheetDragHandle = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(Dp.Hairline.plus(2.dp))
                        .padding(top = 12.dp)
                        .background(SpoonyAndroidTheme.colors.gray300)
                )
                dragHandle()
            }
        }
    ) {
        content()
    }
}
