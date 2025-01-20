package com.spoony.spoony.presentation.explore.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicBottomSheet
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import kotlinx.collections.immutable.persistentListOf

val LOCATION_LIST = persistentListOf(
    "서울",
    "경기",
    "부산",
    "대구",
    "인천",
    "광주",
    "대전",
    "울산",
    "세종"
)

val CITY_LIST = persistentListOf(
    "종로구",
    "중구",
    "용산구",
    "성동구",
    "광진구",
    "동대문구",
    "중랑구",
    "성북구",
    "강북구",
    "마포구"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreLocationBottomSheet(
    selectedCity: String,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit
) {
    val density = LocalDensity.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false }
    )

    var currentCity by remember { mutableStateOf(selectedCity) }
    var columnHeight by remember { mutableIntStateOf(0) }

    GetColumnHeight(
        onHeightMeasured = {
            columnHeight = it
        }
    )

    SpoonyBasicBottomSheet(
        onDismiss = onDismiss,
        sheetState = sheetState,
        dragHandle = { ExploreLocationDragHandle(onClick = onDismiss) }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { (columnHeight * 9).toDp() })
            ) {
                Column(
                    modifier = Modifier
                        .weight(128 / 360f)
                ) {
                    LOCATION_LIST.forEach { location ->
                        key(location) {
                            LocationItem(
                                location = location,
                                isSelected = location == "서울",
                                onClick = {}
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(232 / 360f)
                ) {
                    items(
                        items = CITY_LIST
                    ) { city ->
                        key(city) {
                            CityItem(
                                city = city,
                                isSelected = city == currentCity,
                                onClick = { currentCity = city }
                            )
                        }
                    }
                }
            }
            SpoonyButton(
                text = "선택하기",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                onClick = { onClick(currentCity) },
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

@Composable
private fun LocationItem(
    location: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = location,
        style = SpoonyAndroidTheme.typography.body2m,
        color = if (isSelected) SpoonyAndroidTheme.colors.black else SpoonyAndroidTheme.colors.gray400,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(if (location == "서울") SpoonyAndroidTheme.colors.white else SpoonyAndroidTheme.colors.gray0)
            .border(1.dp, color = SpoonyAndroidTheme.colors.gray0)
            .clickable(
                onClick = onClick,
                enabled = location == "서울"
            )
            .padding(vertical = 12.dp)
    )
}

@Composable
private fun CityItem(
    city: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = city,
        style = SpoonyAndroidTheme.typography.body2m,
        color = if (isSelected) SpoonyAndroidTheme.colors.black else SpoonyAndroidTheme.colors.gray400,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    )
}

@Composable
private fun GetColumnHeight(
    onHeightMeasured: (Int) -> Unit
) {
    CityItem(
        city = "",
        isSelected = false,
        onClick = {},
        modifier = Modifier
            .onGloballyPositioned {
                onHeightMeasured(it.size.height)
            }
    )
}
