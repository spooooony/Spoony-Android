package com.spoony.spoony.core.designsystem.component.datepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import java.util.Calendar

@Composable
fun SpoonyDatePicker(
    modifier: Modifier = Modifier,
    initialYear: Int = 2000,
    initialMonth: Int = 1,
    initialDay: Int = 1,
    onDateChange: (year: Int, month: Int, day: Int) -> Unit = { _, _, _ -> }
) {
    var selectedYear by remember { mutableStateOf(initialYear) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedDay by remember { mutableStateOf(initialDay) }

    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR) }
    val currentMonth = remember { Calendar.getInstance().get(Calendar.MONTH) + 1 }
    val currentDay = remember { Calendar.getInstance().get(Calendar.DAY_OF_MONTH) }

    val maxYear = remember { currentYear - 14 }

    val years = remember { (1900..maxYear).map { "$it 년" } }

    val months = remember(selectedYear, maxYear, currentMonth) {
        if (selectedYear < maxYear) {
            (1..12).map { "$it 월" }
        } else {
            (1..currentMonth).map { "$it 월" }
        }
    }

    val maxDaysInMonth by remember(selectedYear, selectedMonth) {
        derivedStateOf {
            getDaysInMonth(selectedYear, selectedMonth)
        }
    }

    val days by remember(selectedYear, selectedMonth, maxDaysInMonth, maxYear, currentMonth, currentDay) {
        derivedStateOf {
            if (selectedYear < maxYear || (selectedYear == maxYear && selectedMonth < currentMonth)) {
                (1..maxDaysInMonth).map { "$it 일" }
            } else {
                (1..minOf(currentDay, maxDaysInMonth)).map { "$it 일" }
            }
        }
    }

    val isDateValid by remember(selectedYear, selectedMonth, selectedDay) {
        derivedStateOf {
            isValidDate(selectedYear, selectedMonth, selectedDay, currentYear, currentMonth, currentDay)
        }
    }

    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        if (isDateValid) {
            onDateChange(selectedYear, selectedMonth, selectedDay)
        }
    }

    LaunchedEffect(selectedYear, selectedMonth) {
        if (selectedDay > maxDaysInMonth) {
            selectedDay = maxDaysInMonth
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SpoonyBasicPicker(
                items = years,
                initialSelectedIndex = years.indexOfFirst { it.startsWith(selectedYear.toString()) },
                onSelectedItemChanged = { selectedItem ->
                    val year = selectedItem.split(" ")[0].toInt()
                    selectedYear = year

                    if (selectedYear == maxYear && selectedMonth > currentMonth) {
                        selectedMonth = currentMonth
                    }

                    if (selectedYear == maxYear && selectedMonth == currentMonth && selectedDay > currentDay) {
                        selectedDay = currentDay
                    }
                },
                modifier = Modifier.weight(1f)
            )

            SpoonyBasicPicker(
                items = months,
                initialSelectedIndex = selectedMonth - 1,
                onSelectedItemChanged = { selectedItem ->
                    val month = selectedItem.split(" ")[0].toInt()
                    selectedMonth = month

                    if (selectedYear == maxYear && selectedMonth == currentMonth && selectedDay > currentDay) {
                        selectedDay = currentDay
                    }
                },
                modifier = Modifier.weight(1f)
            )

            SpoonyBasicPicker(
                items = days,
                initialSelectedIndex = (selectedDay - 1).coerceIn(0, days.size - 1),
                onSelectedItemChanged = { selectedItem ->
                    val day = selectedItem.split(" ")[0].toInt()
                    selectedDay = day
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        2 -> if (isLeapYear(year)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
}

private fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

private fun isValidDate(year: Int, month: Int, day: Int, currentYear: Int, currentMonth: Int, currentDay: Int): Boolean {
    val maxYear = currentYear - 14

    return when {
        year < maxYear -> true
        year > maxYear -> false
        month < currentMonth -> true
        month > currentMonth -> false
        else -> day <= currentDay
    }
}

@Preview
@Composable
fun SpoonyDatePickerPreview() {
    SpoonyAndroidTheme {
        Surface {
            SpoonyDatePicker()
        }
    }
}
