package com.spoony.spoony.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.component.chip.IconChip
import com.spoony.spoony.core.designsystem.component.slider.SpoonySlider
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyIconButtonTextField
import com.spoony.spoony.core.designsystem.component.textfield.SpoonySearchTextField
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.core.util.extension.advancedImePadding
import com.spoony.spoony.presentation.register.RegisterViewModel.Companion.MAX_MENU_COUNT
import com.spoony.spoony.presentation.register.component.AddMenuButton
import com.spoony.spoony.presentation.register.component.CustomDropDownMenu
import com.spoony.spoony.presentation.register.component.DropdownMenuItem
import com.spoony.spoony.presentation.register.component.NextButton
import com.spoony.spoony.presentation.register.component.SearchResultItem
import com.spoony.spoony.presentation.register.model.CategoryState
import com.spoony.spoony.presentation.register.model.PlaceState
import com.spoony.spoony.presentation.register.model.RegisterState
import com.spoony.spoony.presentation.register.model.RegisterType
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RegisterStartRoute(
    onNextClick: () -> Unit,
    onInitialProgress: () -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val registerType = viewModel.registerType

    val isNextButtonEnabled = remember(
        state.selectedPlace,
        state.selectedCategory,
        state.menuList
    ) {
        viewModel.checkFirstStepValidation()
    }

    LaunchedEffect(Unit) {
        onInitialProgress()
    }

    RegisterStartScreen(
        state = state,
        isNextButtonEnabled = isNextButtonEnabled,
        onNextClick = onNextClick,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onSearchAction = viewModel::searchPlace,
        onPlaceSelect = viewModel::selectPlace,
        onPlaceClear = viewModel::clearSelectedPlace,
        onDismissSearchResults = viewModel::clearSearchResults,
        onSelectCategory = viewModel::selectCategory,
        onMenuUpdate = viewModel::updateMenu,
        onMenuRemove = viewModel::removeMenu,
        onMenuAdd = viewModel::addMenu,
        onSliderPositionChange = viewModel::updateUserSatisfactionValue,
        modifier = modifier,
        registerType = registerType
    )
}

@Composable
private fun RegisterStartScreen(
    registerType: RegisterType,
    state: RegisterState,
    isNextButtonEnabled: Boolean,
    onNextClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchAction: (String) -> Unit,
    onPlaceSelect: (PlaceState) -> Unit,
    onPlaceClear: () -> Unit,
    onDismissSearchResults: () -> Unit,
    onSelectCategory: (CategoryState) -> Unit,
    onMenuUpdate: (Int, String) -> Unit,
    onMenuRemove: (Int) -> Unit,
    onMenuAdd: () -> Unit,
    onSliderPositionChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val isCleanerIconVisible = registerType == RegisterType.CREATE

    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .advancedImePadding()
            .verticalScroll(rememberScrollState())
            .padding(top = 22.dp, bottom = 17.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "나의 찐맛집을 등록해볼까요?",
            style = SpoonyAndroidTheme.typography.title3b,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(28.dp))

        PlaceSearchSection(
            place = state.selectedPlace,
            searchQuery = state.searchQuery,
            searchResults = state.searchResults,
            onSearchQueryChange = onSearchQueryChange,
            onSearchAction = onSearchAction,
            onPlaceSelect = { place ->
                onPlaceSelect(place)
                focusManager.clearFocus()
            },
            onPlaceClear = onPlaceClear,
            onDismissSearchResults = onDismissSearchResults,
            isCleanerIconVisible = isCleanerIconVisible
        )

        Spacer(modifier = Modifier.height(32.dp))

        CategorySection(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            onSelectCategory = onSelectCategory
        )

        Spacer(modifier = Modifier.height(32.dp))

        MenuSection(
            menuList = state.menuList,
            onMenuUpdate = onMenuUpdate,
            onMenuRemove = onMenuRemove,
            onMenuAdd = onMenuAdd
        )

        Spacer(modifier = Modifier.height(32.dp))

        SliderSection(
            sliderPosition = state.userSatisfactionValue,
            onSliderPositionChange = onSliderPositionChange
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(12.dp))

        NextButton(
            enabled = isNextButtonEnabled,
            onClick = onNextClick
        )
    }
}

@Composable
private fun PlaceSearchSection(
    place: PlaceState,
    searchQuery: String,
    searchResults: ImmutableList<PlaceState>,
    onSearchQueryChange: (String) -> Unit,
    onSearchAction: (String) -> Unit,
    onPlaceSelect: (PlaceState) -> Unit,
    onPlaceClear: () -> Unit,
    onDismissSearchResults: () -> Unit,
    isCleanerIconVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "장소명을 알려주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box {
            var textFieldSize by remember { mutableIntStateOf(0) }
            val density = LocalDensity.current

            if (place.placeName.isEmpty()) {
                SpoonySearchTextField(
                    value = searchQuery,
                    onValueChanged = onSearchQueryChange,
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
                        if (searchQuery.isNotEmpty()) {
                            onSearchAction(searchQuery)
                        }
                    }
                )

                if (searchResults.isNotEmpty()) {
                    SearchResultsList(
                        results = searchResults,
                        textFieldSize = textFieldSize,
                        onDismiss = onDismissSearchResults,
                        onItemClick = onPlaceSelect
                    )
                }
            } else {
                SearchResultItem(
                    placeName = place.placeName,
                    placeRoadAddress = place.placeRoadAddress,
                    onDeleteClick = onPlaceClear,
                    isCleanerIconVisible = isCleanerIconVisible
                )
            }
        }
    }
}

@Composable
private fun SearchResultsList(
    results: ImmutableList<PlaceState>,
    textFieldSize: Int,
    onDismiss: () -> Unit,
    onItemClick: (PlaceState) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomDropDownMenu(
        onDismissRequest = onDismiss,
        isVisible = true,
        horizontalPadding = 20.dp,
        modifier = modifier.offset {
            IntOffset(0, (textFieldSize + 4).dp.roundToPx())
        }
    ) {
        results.forEach { place ->
            key(place.placeName + place.placeAddress) {
                DropdownMenuItem(
                    placeName = place.placeName,
                    placeRoadAddress = place.placeRoadAddress,
                    onClick = { onItemClick(place) },
                    showDivider = true
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySection(
    categories: ImmutableList<CategoryState>,
    selectedCategory: CategoryState,
    onSelectCategory: (CategoryState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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
                key(category.categoryId) {
                    IconChip(
                        text = category.categoryName,
                        selectedIconUrl = category.iconUrlSelected,
                        unSelectedIconUrl = category.iconUrlNotSelected,
                        onClick = {
                            onSelectCategory(category)
                        },
                        isSelected = selectedCategory.categoryId == category.categoryId,
                        isGradient = true,
                        secondColor = SpoonyAndroidTheme.colors.white,
                        mainColor = SpoonyAndroidTheme.colors.main400,
                        selectedBorderColor = SpoonyAndroidTheme.colors.main200
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuSection(
    menuList: ImmutableList<String>,
    onMenuUpdate: (Int, String) -> Unit,
    onMenuRemove: (Int) -> Unit,
    onMenuAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "추천 메뉴를 알려주세요",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            menuList.forEachIndexed { index, menu ->
                SpoonyIconButtonTextField(
                    value = menu,
                    onValueChanged = { newValue ->
                        onMenuUpdate(index, newValue)
                    },
                    placeholder = "메뉴 이름",
                    onDeleteClick = { onMenuRemove(index) },
                    showDeleteIcon = menu.isNotBlank() || menuList.size > 1,
                    maxLength = 30,
                    isAllowSpecialChars = true
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = menuList.size < MAX_MENU_COUNT,
            enter = expandVertically(
                animationSpec = tween(300)
            ),
            exit = shrinkVertically(
                animationSpec = tween(300)
            )
        ) {
            AddMenuButton(onClick = onMenuAdd)
        }
    }
}

@Composable
private fun SliderSection(
    sliderPosition: Float,
    onSliderPositionChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "가격 대비 만족도는 어땠나요?",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = SpoonyAndroidTheme.colors.black
        )

        SpoonySlider(
            value = sliderPosition,
            onValueChange = onSliderPositionChange
        )

        Spacer(modifier = Modifier.height(21.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "아쉬움",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black
            )

            Text(
                text = "적당함",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black
            )

            Text(
                text = "훌륭함",
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black
            )
        }
    }
}
