package com.spoony.spoony.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.core.designsystem.component.chip.IconChip
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
import com.spoony.spoony.presentation.register.model.Category
import com.spoony.spoony.presentation.register.model.Place
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RegisterStepOneScreen(
    onNextClick: () -> Unit,
    onInitialProgress: () -> Unit,
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

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
            style = SpoonyAndroidTheme.typography.title2b,
            color = SpoonyAndroidTheme.colors.black
        )

        Spacer(modifier = Modifier.height(28.dp))

        PlaceSearchSection(
            place = state.selectedPlace,
            searchQuery = state.searchQuery,
            searchResults = state.searchResults,
            onSearchQueryChange = viewModel::updateSearchQuery,
            onSearchAction = viewModel::searchPlace,
            onPlaceSelect = { place ->
                viewModel.selectPlace(place)
                focusManager.clearFocus()
            },
            onPlaceClear = viewModel::clearSelectedPlace,
            onDismissSearchResults = viewModel::clearSearchResults
        )

        Spacer(modifier = Modifier.height(32.dp))

        CategorySection(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            onSelectCategory = viewModel::selectCategory
        )

        Spacer(modifier = Modifier.height(32.dp))

        MenuSection(
            menuList = state.menuList,
            onMenuUpdate = viewModel::updateMenu,
            onMenuRemove = viewModel::removeMenu,
            onMenuAdd = viewModel::addMenu
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(20.dp))

        NextButton(
            enabled = isNextButtonEnabled,
            onClick = onNextClick
        )
    }
}

@Composable
private fun PlaceSearchSection(
    place: Place,
    searchQuery: String,
    searchResults: ImmutableList<Place>,
    onSearchQueryChange: (String) -> Unit,
    onSearchAction: (String) -> Unit,
    onPlaceSelect: (Place) -> Unit,
    onPlaceClear: () -> Unit,
    onDismissSearchResults: () -> Unit,
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
                    onDeleteClick = onPlaceClear
                )
            }
        }
    }
}

@Composable
private fun SearchResultsList(
    results: ImmutableList<Place>,
    textFieldSize: Int,
    onDismiss: () -> Unit,
    onItemClick: (Place) -> Unit,
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
    categories: ImmutableList<Category>,
    selectedCategory: Category,
    onSelectCategory: (Category) -> Unit,
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
                        isSelected = selectedCategory.categoryId == category.categoryId
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    maxLength = 30
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = menuList.size < MAX_MENU_COUNT,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            AddMenuButton(onClick = onMenuAdd)
        }
    }
}

