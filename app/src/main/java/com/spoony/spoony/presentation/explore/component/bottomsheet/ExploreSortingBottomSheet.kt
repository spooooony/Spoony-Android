package com.spoony.spoony.presentation.explore.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicBottomSheet
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.type.SortingOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreSortingBottomSheet(
    onDismiss: () -> Unit,
    onClick: (SortingOption) -> Unit,
    currentSortingOption: SortingOption = SortingOption.LATEST
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { false }
    )

    SpoonyBasicBottomSheet(
        onDismiss = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            SortingOption.entries.forEach { sortingOption ->
                Text(
                    text = sortingOption.stringValue,
                    style = SpoonyAndroidTheme.typography.body2b,
                    color = if (currentSortingOption == sortingOption) {
                        SpoonyAndroidTheme.colors.gray900
                    } else {
                        SpoonyAndroidTheme.colors.gray400
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .noRippleClickable {
                            onClick(sortingOption)
                            onDismiss()
                        }
                        .background(
                            if (currentSortingOption == sortingOption) {
                                SpoonyAndroidTheme.colors.gray0
                            } else {
                                SpoonyAndroidTheme.colors.white
                            }
                        )
                        .padding(vertical = 18.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
            }

            SpoonyButton(
                text = "취소",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
