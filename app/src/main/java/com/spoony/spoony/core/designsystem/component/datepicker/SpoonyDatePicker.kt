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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import java.util.Calendar

private val CURRENT_CALENDAR = Calendar.getInstance()
private val CURRENT_YEAR = CURRENT_CALENDAR.get(Calendar.YEAR)
private val CURRENT_MONTH = CURRENT_CALENDAR.get(Calendar.MONTH) + 1
private val CURRENT_DAY = CURRENT_CALENDAR.get(Calendar.DAY_OF_MONTH)
private val MAX_YEAR = CURRENT_YEAR - 13
private const val MIN_YEAR = 1900

private val YEARS = (MIN_YEAR..MAX_YEAR).map { "$it  년" }
private val ALL_MONTHS = (1..12).map { "$it  월" }
private val DAYS_28 = (1..28).map { "$it  일" }
private val DAYS_29 = (1..29).map { "$it  일" }
private val DAYS_30 = (1..30).map { "$it  일" }
private val DAYS_31 = (1..31).map { "$it  일" }

@Composable
fun SpoonyDatePicker(
    modifier: Modifier = Modifier,
    initialYear: Int = 2000,
    initialMonth: Int = 1,
    initialDay: Int = 1,
    onDateChange: (year: Int, month: Int, day: Int) -> Unit = { _, _, _ -> }
) {
    var selectedYear by remember { mutableIntStateOf(initialYear) }
    var selectedMonth by remember { mutableIntStateOf(initialMonth) }
    var selectedDay by remember { mutableIntStateOf(initialDay) }

    val months by remember(selectedYear, CURRENT_MONTH) {
        derivedStateOf {
            if (selectedYear < MAX_YEAR) {
                ALL_MONTHS
            } else {
                ALL_MONTHS.take(CURRENT_MONTH)
            }
        }
    }

    val days by remember(selectedYear, selectedMonth, CURRENT_MONTH, CURRENT_DAY) {
        derivedStateOf {
            val daysInMonth = daysInMonthList(selectedYear, selectedMonth)

            if (selectedYear < MAX_YEAR || (selectedYear == MAX_YEAR && selectedMonth < CURRENT_MONTH)) {
                daysInMonth
            } else {
                daysInMonth.take(CURRENT_DAY)
            }
        }
    }

    val isDateValid by remember(selectedYear, selectedMonth, selectedDay) {
        derivedStateOf {
            isValidDate(selectedYear, selectedMonth, selectedDay)
        }
    }

    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        if (isDateValid) {
            onDateChange(selectedYear, selectedMonth, selectedDay)
        }
    }

    LaunchedEffect(selectedYear, selectedMonth) {
        val daysInMonth = daysInMonthList(selectedYear, selectedMonth)
        if (selectedDay > daysInMonth.size) {
            selectedDay = daysInMonth.size
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
                items = YEARS,
                initialSelectedIndex = (selectedYear - MIN_YEAR).coerceIn(0, YEARS.size - 1),
                onSelectedItemChanged = { selectedItem ->
                    val year = selectedItem.split(" ")[0].toInt()
                    selectedYear = year

                    if (selectedYear == MAX_YEAR && selectedMonth > CURRENT_MONTH) {
                        selectedMonth = CURRENT_MONTH
                    }

                    if (selectedYear == MAX_YEAR && selectedMonth == CURRENT_MONTH && selectedDay > CURRENT_DAY) {
                        selectedDay = CURRENT_DAY
                    }
                },
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )

            SpoonyBasicPicker(
                items = months,
                initialSelectedIndex = (selectedMonth - 1).coerceIn(0, months.size - 1),
                onSelectedItemChanged = { selectedItem ->
                    val month = selectedItem.split(" ")[0].toInt()
                    selectedMonth = month

                    if (selectedYear == MAX_YEAR && selectedMonth == CURRENT_MONTH && selectedDay > CURRENT_DAY) {
                        selectedDay = CURRENT_DAY
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
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun daysInMonthList(year: Int, month: Int): List<String> = when (month) {
    2 -> if (isLeapYear(year)) DAYS_29 else DAYS_28
    4, 6, 9, 11 -> DAYS_30
    else -> DAYS_31
}

private fun isLeapYear(year: Int): Boolean = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)

private fun isValidDate(year: Int, month: Int, day: Int): Boolean = when {
    year < MAX_YEAR -> true
    year > MAX_YEAR -> false
    month < CURRENT_MONTH -> true
    month > CURRENT_MONTH -> false
    else -> day <= CURRENT_DAY
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
