package com.spoony.spoony.core.designsystem.component.datepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray200
import com.spoony.spoony.core.util.extension.fadingEdge
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
    
    val years = remember { (1900..2011).map { "$it 년" } }
    val months = remember { (1..12).map { "$it 월" } }
    
    val maxDaysInMonth by remember(selectedYear, selectedMonth) {
        derivedStateOf {
            getDaysInMonth(selectedYear, selectedMonth)
        }
    }
    
    val days by remember(selectedYear, selectedMonth, maxDaysInMonth) {
        derivedStateOf {
            (1..maxDaysInMonth).map { "$it 일" }
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
        Text(
            text = "생년월일",
            style = SpoonyAndroidTheme.typography.body1b,
        )
        
        Spacer(modifier = Modifier.height(11.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DatePickerColumn(
                items = years,
                initialSelectedIndex = years.indexOfFirst { it.startsWith(selectedYear.toString()) },
                onSelectedItemChanged = { selectedItem ->
                    val year = selectedItem.split(" ")[0].toInt()
                    selectedYear = year
                },
                modifier = Modifier.weight(1f)
            )
            
            DatePickerColumn(
                items = months,
                initialSelectedIndex = selectedMonth - 1,
                onSelectedItemChanged = { selectedItem ->
                    val month = selectedItem.split(" ")[0].toInt()
                    selectedMonth = month
                },
                modifier = Modifier.weight(1f)
            )
            
            DatePickerColumn(
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DatePickerColumn(
    items: List<String>,
    initialSelectedIndex: Int,
    onSelectedItemChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 5
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initialSelectedIndex - visibleItemsCount / 2).coerceAtLeast(0)
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    
    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.value.toDp() }
    
    val visibleItemsMiddle = visibleItemsCount / 2
    
    val paddedItems = remember(items) {
        val paddingCount = visibleItemsCount / 2
        List(paddingCount) { "" } + items + List(paddingCount) { "" }
    }
    
    val selectedIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + visibleItemsMiddle
        }
    }
    
    LaunchedEffect(selectedIndex) {
        val actualIndex = selectedIndex - visibleItemsMiddle
        if (actualIndex in items.indices) {
            onSelectedItemChanged(items[actualIndex])
        }
    }
    
    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to gray200,
            0.5f to Color.Black,
            1f to gray200
        )
    }
    
    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(paddedItems) { item ->
                val isSelected = paddedItems.indexOf(item) == selectedIndex
                
                Text(
                    text = item,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = SpoonyAndroidTheme.typography.body2m,
                    color = if (isSelected) 
                        SpoonyAndroidTheme.colors.gray900 
                    else 
                        SpoonyAndroidTheme.colors.gray300,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        HorizontalDivider(
            color = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle)
        )
        
        HorizontalDivider(
            color = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1))
        )
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
    val maxYear = minOf(currentYear, 2011)
    
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
        Surface(modifier = Modifier.fillMaxSize()) {
            SpoonyDatePicker()
        }
    }
}
