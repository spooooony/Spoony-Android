package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.datepicker.SpoonyDatePicker
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonyDatePickerBottomSheet(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialYear: Int = 2000,
    initialMonth: Int = 1,
    initialDay: Int = 1
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    var selectedYear by remember { mutableIntStateOf(initialYear) }
    var selectedMonth by remember { mutableIntStateOf(initialMonth) }
    var selectedDay by remember { mutableIntStateOf(initialDay) }

    SpoonyBasicBottomSheet(
        onDismiss = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        dragHandle = {
            SpoonyTitleDragHandle(
                onClick = onDismiss,
                text = "생년월일"
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(12.dp))
            SpoonyDatePicker(
                initialYear = initialYear,
                initialMonth = initialMonth,
                initialDay = initialDay,
                onDateChange = { year, month, day ->
                    selectedYear = year
                    selectedMonth = month
                    selectedDay = day
                }
            )
            SpoonyButton(
                text = "완료",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                onClick = {
                    val calendar = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth - 1, selectedDay)
                    }
                    val formattedDate = formatter.format(calendar.time)

                    onDateSelected(formattedDate)
                    onDismiss()
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        bottom = 20.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpoonyDatePickerBottomSheetPreview() {
    SpoonyAndroidTheme {
        var isBottomSheetVisible by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedDate,
                modifier = Modifier.padding(16.dp)
            )

            Surface(
                modifier = Modifier,
                color = main400,
                contentColor = white,
                onClick = { isBottomSheetVisible = true }
            ) {
                Text(
                    text = "테스트 버튼",
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (isBottomSheetVisible) {
                SpoonyDatePickerBottomSheet(
                    onDismiss = { isBottomSheetVisible = false },
                    onDateSelected = {
                        selectedDate = it
                    }
                )
            }
        }
    }
}
