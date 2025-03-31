package com.spoony.spoony.presentation.gourmet.map.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicDragHandle
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun MapBottomSheetDragHandle(
    name: String,
    resultCount: Int,
    modifier: Modifier = Modifier
) {
    SpoonyBasicDragHandle {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = "${name}님의 찐맛집",
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.gray900
            )
            Text(
                text = resultCount.toString(),
                style = SpoonyAndroidTheme.typography.body2b,
                color = SpoonyAndroidTheme.colors.gray500,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapBottomSheetDragHandlePreview() {
    SpoonyAndroidTheme {
        MapBottomSheetDragHandle(
            name = "제이",
            resultCount = 5
        )
    }
}
