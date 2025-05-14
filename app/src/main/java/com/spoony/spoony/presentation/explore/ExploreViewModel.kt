package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.CategoryRepository
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.presentation.explore.model.FilterType
import com.spoony.spoony.presentation.explore.model.toModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val authRepository: AuthRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreState> = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState>
        get() = _state

    private val _sideEffect = Channel<ExploreSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        getAllFeedList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            categoryRepository.getCategories()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            categoryList = UiState.Success(response.toImmutableList())
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            categoryList = UiState.Failure("카테고리 목록 조회 실패")
                        )
                    }
                }
        }
    }

    fun localReviewToggle() {
        val chipItems = _state.value.chipItems
        val currentFilterState = _state.value.filterSelectionState
        val propertySelectedState = currentFilterState.properties
        val isSelected = !(propertySelectedState[1] ?: false)
        val updatedFilterOptions = chipItems.map { option ->
            when (option.sort) {
                FilterType.LOCAL_REVIEW -> {
                    option.copy(isSelected = isSelected)
                }
                else -> option
            }
        }.toImmutableList()
        _state.update {
            it.copy(
                filterSelectionState = currentFilterState.copy(
                    properties = propertySelectedState.put(1, isSelected)
                ),
                chipItems = updatedFilterOptions
            )
        }
    }

    fun applyExploreFilter(propertySelectedState: PersistentMap<Int, Boolean>, categorySelectedState: PersistentMap<Int, Boolean>, regionSelectedState: PersistentMap<Int, Boolean>, ageSelectedState: PersistentMap<Int, Boolean>) {
        val chipItems = _state.value.chipItems
        val currentFilterState = _state.value.filterSelectionState
        val currentFilterItems = _state.value.exploreFilterItems
        val categoryItems = currentFilterItems.categories
        val regionItems = currentFilterItems.regions
        val ageItems = currentFilterItems.ages
        val updatedFilterOptions = chipItems.map { option ->
            when (option.sort) {
                FilterType.LOCAL_REVIEW -> {
                    val isSelected = propertySelectedState[1] ?: false
                    option.copy(isSelected = isSelected)
                }
                FilterType.CATEGORY -> {
                    val selectedCategories = categorySelectedState.filter { it.value }
                    val isSelected = selectedCategories.isNotEmpty()
                    val updatedText = when {
                        !isSelected -> "카테고리"
                        selectedCategories.size == 1 -> {
                            categoryItems.find { it.id == selectedCategories.keys.first() }?.name ?: "카테고리"
                        }
                        else -> {
                            val firstSelected = categoryItems.find { it.id == selectedCategories.keys.min() }?.name ?: ""
                            "$firstSelected 외 ${selectedCategories.size - 1}개"
                        }
                    }
                    option.copy(isSelected = isSelected, text = updatedText)
                }
                FilterType.REGION -> {
                    val selectedRegions = regionSelectedState.filter { it.value }
                    val isSelected = selectedRegions.isNotEmpty()
                    val updatedText = when {
                        !isSelected -> "지역"
                        selectedRegions.size == 1 -> {
                            regionItems.find { it.id == selectedRegions.keys.first() }?.name ?: "지역"
                        }
                        else -> {
                            val firstSelected = regionItems.find { it.id == selectedRegions.keys.min() }?.name ?: ""
                            "$firstSelected 외 ${selectedRegions.size - 1}개"
                        }
                    }
                    option.copy(isSelected = isSelected, text = updatedText)
                }
                FilterType.AGE -> {
                    val selectedAges = ageSelectedState.filter { it.value }
                    val isSelected = selectedAges.isNotEmpty()
                    val updatedText = when {
                        !isSelected -> "연령대"
                        selectedAges.size == 1 -> {
                            ageItems.find { it.id == selectedAges.keys.first() }?.name ?: "연령대"
                        }
                        else -> {
                            val firstSelected = ageItems.find { it.id == selectedAges.keys.min() }?.name ?: ""
                            "$firstSelected 외 ${selectedAges.size - 1}개"
                        }
                    }
                    option.copy(isSelected = isSelected, text = updatedText)
                }
                FilterType.FILTER -> {
                    val isSelected = propertySelectedState.any { (_, selected) -> selected } ||
                        categorySelectedState.any { (_, selected) -> selected } ||
                        regionSelectedState.any { (_, selected) -> selected } ||
                        ageSelectedState.any { (_, selected) -> selected }
                    option.copy(isSelected = isSelected)
                }
            }
        }.toImmutableList()
        _state.update {
            it.copy(
                filterSelectionState = currentFilterState.copy(
                    properties = propertySelectedState,
                    categories = categorySelectedState,
                    regions = regionSelectedState,
                    ages = ageSelectedState
                ),
                chipItems = updatedFilterOptions
            )
        }
    }

    fun resetExploreFilter() {
        val currentFilterState = _state.value.filterSelectionState
        _state.update {
            it.copy(
                filterSelectionState = currentFilterState.copy(
                    properties = persistentMapOf(),
                    categories = persistentMapOf(),
                    regions = persistentMapOf(),
                    ages = persistentMapOf()
                )
            )
        }
    }

    private fun getAllFeedList() {
        viewModelScope.launch {
            try {
                exploreRepository.getAllFeedList()
                    .onSuccess { response ->
                        _state.update {
                            it.copy(
                                placeReviewList =
                                if (response.isEmpty()) {
                                    UiState.Empty
                                } else {
                                    UiState.Success(
                                        response.map { placeReview ->
                                            placeReview.toModel()
                                        }.toImmutableList()
                                    )
                                }
                            )
                        }
                    }
                    .onFailure {
                        _state.update {
                            it.copy(
                                placeReviewList = UiState.Failure("피드 목록 조회 실패")
                            )
                        }
                        _sideEffect.send(ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                    }
            } catch (e: Exception) {
                _sideEffect.send(ExploreSideEffect.ShowSnackbar("예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."))
            }
        }
    }

    fun updateSelectedSortingOption(sortingOption: SortingOption) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedSortingOption = sortingOption
                )
            }
        }
    }
}

sealed class ExploreSideEffect {
    data class ShowSnackbar(val message: String) : ExploreSideEffect()
}
