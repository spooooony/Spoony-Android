package com.spoony.spoony.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.model.RegionModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

private val CITY_LIST = persistentListOf(
    "서울",
    "경기",
    "인천",
    "강원",
    "부산",
    "대전",
    "대구",
    "제주",
    "울산",
    "경남",
    "경북",
    "충남",
    "충북",
    "세종",
    "전남",
    "전북",
    "광주"
)

val regionList = persistentListOf(
    RegionModel(0, "강남구"),
    RegionModel(1, "강동구"),
    RegionModel(2, "강북구"),
    RegionModel(3, "강서구"),
    RegionModel(4, "관악구"),
    RegionModel(5, "광진구"),
    RegionModel(6, "구로구"),
    RegionModel(7, "금천구"),
    RegionModel(8, "노원구"),
    RegionModel(9, "도봉구"),
    RegionModel(10, "동대문구"),
    RegionModel(11, "동작구"),
    RegionModel(12, "마포구"),
    RegionModel(13, "서대문구"),
    RegionModel(14, "서초구"),
    RegionModel(15, "성동구"),
    RegionModel(16, "성북구"),
    RegionModel(17, "송파구"),
    RegionModel(18, "양천구"),
    RegionModel(19, "영등포구"),
    RegionModel(20, "용산구"),
    RegionModel(21, "은평구"),
    RegionModel(22, "종로구"),
    RegionModel(23, "중구"),
    RegionModel(24, "중랑구")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonyRegionBottomSheet(
    regionList: ImmutableList<RegionModel>,
    onDismiss: () -> Unit,
    onClick: (RegionModel) -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var selectedCity by remember { mutableStateOf("서울") }
    var selectedRegion by remember { mutableStateOf(RegionModel(-1, "")) }
    var columnHeight by remember { mutableIntStateOf(0) }

    val handleOnDismiss: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    LaunchedEffect(selectedCity) {
        selectedRegion = RegionModel(-1, "")
    }

    SpoonyBasicBottomSheet(
        onDismiss = handleOnDismiss,
        sheetState = sheetState,
        dragHandle = { SpoonyTitleDragHandle(onClick = handleOnDismiss) }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { (columnHeight * 9).toDp() })
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(128 / 360f)
                ) {
                    items(
                        items = CITY_LIST,
                        key = { it }
                    ) { city ->
                        RegionItem(
                            region = city,
                            isSelected = city == selectedCity,
                            onClick = { selectedCity = city },
                            modifier = Modifier
                                .background(if (city == selectedCity) SpoonyAndroidTheme.colors.white else SpoonyAndroidTheme.colors.gray0)
                                .border(1.dp, color = SpoonyAndroidTheme.colors.gray0)
                                .onGloballyPositioned {
                                    if (columnHeight == 0) {
                                        columnHeight = it.size.height
                                    }
                                }
                        )
                    }
                }

                if (selectedCity == "서울") {
                    LazyColumn(
                        modifier = Modifier
                            .weight(232 / 360f)
                    ) {
                        items(
                            items = regionList
                        ) { region ->
                            with(region) {
                                key(regionId) {
                                    RegionItem(
                                        region = regionName,
                                        isSelected = regionId == selectedRegion.regionId,
                                        onClick = { selectedRegion = region }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(232 / 360f)
                    ) {
                        Text(
                            text = "지금은 서울만 가능해요!\n다른 지역은 열심히 준비 중이에요.",
                            style = SpoonyAndroidTheme.typography.body2m,
                            color = SpoonyAndroidTheme.colors.gray400,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            SpoonyButton(
                text = "선택하기",
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                onClick = {
                    onClick(selectedRegion)
                    handleOnDismiss()
                },
                enabled = selectedRegion.regionId != -1,
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
private fun RegionItem(
    region: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = region,
        style = SpoonyAndroidTheme.typography.body2m,
        color = if (isSelected) SpoonyAndroidTheme.colors.black else SpoonyAndroidTheme.colors.gray400,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    )
}
