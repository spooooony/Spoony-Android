package com.spoony.spoony.core.designsystem.component.picker

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * 스푸니 생년월일 선택 컴포넌트
 *
 * 성능 최적화 및 기능 개선:
 * 1. 무한 스크롤 제거: 불필요한 리컴포지션 방지
 * 2. 년/월/일 레이블 추가: 선택된 항목 옆에 오버레이로 표시
 * 3. 만 14세 제한 기능: 현재 날짜 기준으로 만 14세 이상만 선택 가능
 * 4. 정적 리스트 사용: DatePicker.kt와 동일한 방식으로 정적 리스트 생성
 * 5. 가시성 개선: 위아래로 2개씩 더 보이도록 수정
 * 6. 오버레이 정렬: 년, 월, 일 레이블이 겹치지 않도록 수정
 * 7. 선택 범위 개선: 구분선 안쪽으로 정확히 선택되도록 조정
 *
 * @param modifier 컴포넌트 수정자
 * @param initialDate 초기 선택 날짜
 * @param onDateSelected 날짜 선택 콜백
 */
@Composable
fun SpoonyBirthPicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(ZoneId.of("Asia/Seoul")),
    onDateSelected: (LocalDate) -> Unit = {}
) {
    // 현재 날짜 기준 만 14세 제한 계산
    val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul"))
    val minYear = 1970
    val maxYear = currentDate.minusYears(14).year

    // 초기값 설정 (만 14세 이상만 선택 가능하도록)
    val initialYear = initialDate.year.coerceIn(minYear, maxYear)
    val initialMonth = initialDate.monthValue
    val initialDay = initialDate.dayOfMonth.coerceAtMost(getDaysInMonth(initialYear, initialMonth))

    // 선택된 값 상태 관리 - mutableIntStateOf 사용으로 불필요한 박싱/언박싱 방지
    var selectedYear by remember { mutableIntStateOf(initialYear) }
    var selectedMonth by remember { mutableIntStateOf(initialMonth) }
    var selectedDay by remember { mutableIntStateOf(initialDay) }

    // 선택된 날짜가 변경될 때 콜백 호출 - derivedStateOf 사용으로 불필요한 재계산 방지
    val selectedDate by remember(selectedYear, selectedMonth, selectedDay) {
        derivedStateOf {
            try {
                LocalDate.of(selectedYear, selectedMonth, selectedDay)
            } catch (e: Exception) {
                // 유효하지 않은 날짜인 경우 (예: 2월 30일) 해당 월의 마지막 날로 조정
                val day = selectedDay.coerceAtMost(getDaysInMonth(selectedYear, selectedMonth))
                LocalDate.of(selectedYear, selectedMonth, day)
            }
        }
    }

    // 날짜가 변경될 때만 콜백 호출
    LaunchedEffect(selectedDate) {
        onDateSelected(selectedDate)
    }

    // 월이 변경될 때 일수가 변경되면 선택된 일이 범위를 벗어나지 않도록 조정
    LaunchedEffect(selectedYear, selectedMonth) {
        val maxDays = getDaysInMonth(selectedYear, selectedMonth)
        if (selectedDay > maxDays) {
            selectedDay = maxDays
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // 년도 선택기
            DateItemsPicker(
                items = years,
                firstIndex = years.indexOfFirst { it == selectedYear.toString() }.coerceAtLeast(1),
                onItemSelected = {
                    if (it.isNotEmpty()) {
                        selectedYear = it.toInt()
                    }
                },
                modifier = Modifier.weight(1f),
                labelText = "년"
            )

            Spacer(modifier = Modifier.width(10.dp))

            // 월 선택기
            DateItemsPicker(
                items = monthsNumber,
                firstIndex = selectedMonth - 1,
                onItemSelected = {
                    if (it.isNotEmpty()) {
                        selectedMonth = it.toInt()
                    }
                },
                modifier = Modifier.weight(1f),
                labelText = "월"
            )

            Spacer(modifier = Modifier.width(10.dp))

            // 일 선택기
            DateItemsPicker(
                items = when {
                    (selectedYear % 4 == 0) && selectedMonth == 2 -> days29
                    selectedMonth == 2 -> days28
                    days30Months.contains(selectedMonth) -> days30
                    else -> days31
                },
                firstIndex = selectedDay - 1,
                onItemSelected = {
                    if (it.isNotEmpty()) {
                        selectedDay = it.toInt()
                    }
                },
                modifier = Modifier.weight(1f),
                labelText = "일"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "선택된 날짜: $selectedYear-${selectedMonth.toString().padStart(2, '0')}-${selectedDay.toString().padStart(2, '0')}",
            style = SpoonyAndroidTheme.typography.body1b
        )
    }
}

/**
 * 날짜 항목 선택기 컴포넌트
 *
 * @param items 선택 가능한 항목 리스트 (앞뒤에 빈 문자열 포함)
 * @param firstIndex 초기 선택 인덱스
 * @param onItemSelected 항목 선택 콜백
 * @param modifier 컴포넌트 수정자
 * @param labelText 선택된 항목 옆에 표시될 레이블 텍스트 (년, 월, 일)
 * @param textStyle 텍스트 스타일
 * @param dividerColor 구분선 색상
 */
@Composable
fun DateItemsPicker(
    items: ImmutableList<String>,
    firstIndex: Int,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    textStyle: TextStyle = SpoonyAndroidTheme.typography.body1m,
    dividerColor: Color = SpoonyAndroidTheme.colors.main400
) {
    if (items.size <= 2) return // 빈 문자열 2개만 있는 경우 처리

    // 리스트 상태 - 초기 인덱스 설정
    val listState = rememberLazyListState(firstIndex)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val currentValue = remember { mutableStateOf("") }

    // 스크롤이 멈추면 선택된 항목 업데이트 및 스냅 처리
    LaunchedEffect(!listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            if (currentValue.value.isNotEmpty()) {
                onItemSelected(currentValue.value)
                listState.animateScrollToItem(index = listState.firstVisibleItemIndex)
            }
        }
    }

    Box(
        modifier = modifier.height(108.dp),
        contentAlignment = Alignment.Center
    ) {
        // 구분선 - DatePicker 코드와 동일한 방식으로 구현
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            HorizontalDivider(
                modifier = Modifier.size(height = 1.dp, width = 60.dp),
                color = dividerColor
            )
            HorizontalDivider(
                modifier = Modifier.size(height = 1.dp, width = 60.dp),
                color = dividerColor
            )
        }

        // 항목 리스트 - DatePicker 코드와 동일한 방식으로 구현
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            flingBehavior = snapBehavior
        ) {
            items(items.size) { index ->
                val firstVisibleItemIndex by remember {
                    derivedStateOf { listState.firstVisibleItemIndex }
                }

                // 현재 선택된 항목 여부 계산 - DatePicker 코드와 동일한 방식으로 구현
                if (index == firstVisibleItemIndex + 1) {
                    currentValue.value = items[index]
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 항목 표시 - 위아래로 2개씩 더 보이도록 수정
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = items[index],
                        modifier = Modifier.alpha(if (index == firstVisibleItemIndex + 1) 1f else 0.3f),
                        style = textStyle,
                        textAlign = TextAlign.Center
                    )

                    // 선택된 항목에만 레이블 표시
                    if (index == firstVisibleItemIndex + 1 && labelText.isNotEmpty() && items[index].isNotEmpty()) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = labelText,
                            style = textStyle,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

/**
 * 윤년 여부 확인
 */
private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

/**
 * 해당 년월의 일수 계산
 */
private fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 31
    }
}

// 현재 날짜 정보
private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
private val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
private val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

// 만 14세 제한 계산
private val minYear = 1970
private val maxYear = Calendar.getInstance().apply {
    add(Calendar.YEAR, -14)
}.get(Calendar.YEAR)

// 정적 리스트 생성 - DatePicker.kt와 동일한 방식으로 구현
// 위아래로 2개씩 더 보이도록 빈 문자열 2개씩 추가
private val years = (listOf("", "") + (minYear..maxYear).map { it.toString() } + listOf("", "")).toImmutableList()
private val monthsNumber = (listOf("", "") + (1..12).map { it.toString() } + listOf("", "")).toImmutableList()
private val days28 = (listOf("", "") + (1..28).map { it.toString() } + listOf("", "")).toImmutableList() // 해당 월 : 2
private val days29 = (listOf("", "") + (1..29).map { it.toString() } + listOf("", "")).toImmutableList() // 해당 월 : 윤년 2월
private val days30 = (listOf("", "") + (1..30).map { it.toString() } + listOf("", "")).toImmutableList() // 해당 월 : 4,6,9,11
private val days31 = (listOf("", "") + (1..31).map { it.toString() } + listOf("", "")).toImmutableList() // 해당 월 : 1,3,5,7,8,10.12
private val days30Months = listOf(4, 6, 9, 11)

@Preview(showBackground = true)
@Composable
private fun SpoonyBirthPickerPreview() {
    SpoonyAndroidTheme {
        SpoonyBirthPicker(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
