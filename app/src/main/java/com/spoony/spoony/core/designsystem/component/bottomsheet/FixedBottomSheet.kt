package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedBottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val halfScreenHeight = LocalConfiguration.current.screenHeightDp.dp / 2

    val sheetState = rememberStandardBottomSheetState(
        confirmValueChange = { false },
        initialValue = SheetValue.PartiallyExpanded
    )

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
        sheetContent = sheetContent,
        modifier = modifier,
        sheetPeekHeight = halfScreenHeight
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun FixedBottomSheetPreview() {
    SpoonyAndroidTheme {
        FixedBottomSheet(
            sheetContent = {}
        ) { }
    }
}
