package com.spoony.spoony.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyIconButtonTextField
import com.spoony.spoony.core.designsystem.component.textfield.SpoonySearchTextField
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ChipColor
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.register.component.AddMenuButton
import com.spoony.spoony.presentation.register.component.CustomDropDownMenu
import com.spoony.spoony.presentation.register.component.DropdownMenuItem
import com.spoony.spoony.presentation.register.component.NextButton
import com.spoony.spoony.presentation.register.component.SearchResultItem

data class CategoryUiModel(
    val categoryId: Int,
    val categoryName: String,
    val iconUrlNotSelected: String,
    val iconUrlSelected: String
)

data class Place(
    val id: String,
    val name: String,
    val roadAddress: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterStepOneScreen(
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var isDropDownVisible by remember { mutableStateOf(false) }
    var selectedPlace: Place? by remember { mutableStateOf(null) }
    var menuList by remember { mutableStateOf(listOf("")) }
    var selectedCategory by remember { mutableStateOf<CategoryUiModel?>(null) }
    val focusManager = LocalFocusManager.current

    // 다음 버튼 활성화 조건
    val isNextButtonEnabled = remember(selectedPlace, selectedCategory, menuList) {
        selectedPlace != null &&
            selectedCategory != null &&
            menuList.any { it.isNotBlank() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .verticalScroll(rememberScrollState())
            .padding(top = 22.dp, bottom = 17.dp)
    ) {
        Text(
            text = "나의 찐맛집을 등록해볼까요?",
            style = SpoonyAndroidTheme.typography.title2b,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "장소명을 알려주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box {
            var textFieldSize by remember { mutableIntStateOf(0) }
            val density = LocalDensity.current

            if (selectedPlace == null) {
                SpoonySearchTextField(
                    value = searchText,
                    onValueChanged = {
                        searchText = it
                        if (it.isEmpty()) {
                            isDropDownVisible = false
                        }
                    },
                    placeholder = "어떤 장소를 한 입 줄까요?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            textFieldSize = with(density) {
                                it.size.height.toDp().value.toInt()
                            }
                        },
                    maxLength = 30,
                    onSearchAction = {
                        if (searchText.isNotEmpty()) {
                            isDropDownVisible = true
                        }
                    }
                )

                if (isDropDownVisible && searchText.isNotEmpty()) {
                    CustomDropDownMenu(
                        onDismissRequest = { isDropDownVisible = false },
                        isVisible = true,
                        horizontalPadding = 20.dp,
                        modifier = Modifier.offset {
                            IntOffset(0, (textFieldSize + 4).dp.roundToPx())
                        }
                    ) {
                        DropdownMenuItem(
                            placeName = "테스트 홍대입구점",
                            placeRoadAddress = "테스트 도로명 주소",
                            onClick = {
                                selectedPlace = Place("1", "테스트 홍대입구점", "테스트 도로명 주소")
                                isDropDownVisible = false
                                focusManager.clearFocus()
                            },
                            showDivider = true
                        )
                    }
                }
            } else {
                SearchResultItem(
                    placeName = selectedPlace!!.name,
                    placeRoadAddress = selectedPlace!!.roadAddress,
                    onResultClick = {},
                    onDeleteClick = {
                        selectedPlace = null
                        searchText = ""
                        isDropDownVisible = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "카테고리를 골라주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                IconChip(
                    text = category.categoryName,
                    tagColor = if (selectedCategory?.categoryId == category.categoryId) ChipColor.Main else ChipColor.White,
                    iconUrl = if (selectedCategory?.categoryId == category.categoryId) {
                        category.iconUrlSelected
                    } else {
                        category.iconUrlNotSelected
                    },
                    onClick = {
                        selectedCategory = if (selectedCategory?.categoryId == category.categoryId) {
                            null
                        } else {
                            category
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "추천 메뉴를 알려주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            menuList.forEachIndexed { index, menu ->
                SpoonyIconButtonTextField(
                    value = menu,
                    onValueChanged = { newValue ->
                        menuList = menuList.toMutableList().apply {
                            set(index, newValue)
                        }
                    },
                    placeholder = "메뉴 이름",
                    onDeleteClick = {
                        menuList = if (menuList.size > 1) {
                            menuList.toMutableList().apply {
                                removeAt(index)
                            }
                        } else {
                            listOf("")
                        }
                    },
                    showDeleteIcon = menuList.size > 1 || menu.isNotEmpty(),
                    maxLength = 30
                )
            }

            if (menuList.size < 3) {
                AddMenuButton(
                    onClick = {
                        menuList = menuList + ""
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        NextButton(
            enabled = isNextButtonEnabled,
            onClick = onNextClick
        )
    }
}

private val categories = listOf(
    CategoryUiModel(1, "로컬 수저", "url_black_2", "url_white_2"),
    CategoryUiModel(2, "한식", "url_black_3", "url_white_3"),
    CategoryUiModel(3, "일식", "url_black_4", "url_white_4"),
    CategoryUiModel(4, "중식", "url_black_5", "url_white_5"),
    CategoryUiModel(5, "양식", "url_black_6", "url_white_6"),
    CategoryUiModel(6, "퓨전/세계요리", "url_black_7", "url_white_7"),
    CategoryUiModel(7, "카페", "url_black_8", "url_white_8"),
    CategoryUiModel(8, "주류", "url_black_9", "url_white_9")
)
