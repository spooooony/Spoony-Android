package com.spoony.spoony.presentation.map.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.textfield.SpoonySearchTextField
import com.spoony.spoony.core.designsystem.component.topappbar.SpoonyBasicTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun MapSearchTopAppBar(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDoneAction: (() -> Unit)
) {
    SpoonyBasicTopAppBar(
        showBackButton = true,
        modifier = modifier
            .background(SpoonyAndroidTheme.colors.white)
    ) {
        SpoonySearchTextField(
            value = value,
            onValueChanged = onValueChanged,
            placeholder = "마포구, 성수동, 강남역",
            modifier = Modifier
                .padding(end = 20.dp),
            onDoneAction = onDoneAction
        )
    }
}
