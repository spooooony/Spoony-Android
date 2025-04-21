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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import java.util.Calendar

// 상수값 미리 정의
private val CURRENT_CALENDAR = Calendar.getInstance()
private val CURRENT_YEAR = CURRENT_CALENDAR.get(Calendar.YEAR)
private val CURRENT_MONTH = CURRENT_CALENDAR.get(Calendar.MONTH) + 1
private val CURRENT_DAY = CURRENT_CALENDAR.get(Calendar.DAY_OF_MONTH)

// 미리 계산된 날짜 리스트들
private val YEARS = (1900..CURRENT_YEAR - 14).map { "$it  년" }
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
    var selectedYear by remember { mutableStateOf(initialYear) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedDay by remember { mutableStateOf(initialDay) }

    val maxYear = CURRENT_YEAR - 14

    // 선택된 연도에 따라 월 목록 필터링
    val months by remember(selectedYear, maxYear, CURRENT_MONTH) {
        derivedStateOf {
            if (selectedYear < maxYear) {
                ALL_MONTHS
            } else {
                ALL_MONTHS.take(CURRENT_MONTH)
            }
        }
    }

    // 선택된 월과 연도에 따른 일 목록
    val days by remember(selectedYear, selectedMonth, maxYear, CURRENT_MONTH, CURRENT_DAY) {
        derivedStateOf {
            // 해당 월의 일 수 결정
            val daysInMonth = when (selectedMonth) {
                2 -> if (isLeapYear(selectedYear)) DAYS_29 else DAYS_28
                4, 6, 9, 11 -> DAYS_30
                else -> DAYS_31
            }
            
            // 현재 날짜 제한 적용
            if (selectedYear < maxYear || (selectedYear == maxYear && selectedMonth < CURRENT_MONTH)) {
                daysInMonth
            } else {
                daysInMonth.take(CURRENT_DAY)
            }
        }
    }

    // 날짜 유효성 확인
    val isDateValid by remember(selectedYear, selectedMonth, selectedDay) {
        derivedStateOf {
            isValidDate(selectedYear, selectedMonth, selectedDay)
        }
    }

    // 유효한 날짜일 때만 콜백 호출
    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        if (isDateValid) {
            onDateChange(selectedYear, selectedMonth, selectedDay)
        }
    }

    // 선택된 일이 해당 월의 최대 일수보다 클 경우 조정
    LaunchedEffect(selectedYear, selectedMonth) {
        val maxDaysInMonth = getDaysInMonth(selectedYear, selectedMonth)
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
                items = YEARS,
                initialSelectedIndex = YEARS.indexOfFirst { it.startsWith(selectedYear.toString()) },
                onSelectedItemChanged = { selectedItem ->
                    val year = selectedItem.split(" ")[0].toInt()
                    selectedYear = year

                    // 날짜 범위 조정
                    if (selectedYear == maxYear && selectedMonth > CURRENT_MONTH) {
                        selectedMonth = CURRENT_MONTH
                    }

                    if (selectedYear == maxYear && selectedMonth == CURRENT_MONTH && selectedDay > CURRENT_DAY) {
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

                    // 날짜 범위 조정
                    if (selectedYear == maxYear && selectedMonth == CURRENT_MONTH && selectedDay > CURRENT_DAY) {
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

private fun isValidDate(year: Int, month: Int, day: Int): Boolean {
    val maxYear = CURRENT_YEAR - 14

    return when {
        year < maxYear -> true
        year > maxYear -> false
        month < CURRENT_MONTH -> true
        month > CURRENT_MONTH -> false
        else -> day <= CURRENT_DAY
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
