package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.CategoryRepository
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.domain.repository.RegionRepository
import com.spoony.spoony.presentation.explore.model.FilterType
import com.spoony.spoony.presentation.explore.model.toExploreFilter
import com.spoony.spoony.presentation.explore.model.toModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val postRepository: PostRepository,
    private val categoryRepository: CategoryRepository,
    private val regionRepository: RegionRepository
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreState> = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ExploreSideEffect>()
    val sideEffect: SharedFlow<ExploreSideEffect>
        get() = _sideEffect.asSharedFlow()

    private var currentCursor: Int? = null
    private var allSearchJob: Job? = null
    private var followingSearchJob: Job? = null

    init {
        getCategoryList()
        getRegionList()
        getPlaceReviewListFiltered()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            categoryRepository.getFoodCategories()
                .onSuccess { response ->
                    val categoryList = response.map { it.toExploreFilter() }.toImmutableList()
                    _state.update {
                        it.copy(
                            exploreFilterItems = it.exploreFilterItems.copy(
                                categories = categoryList
                            )
                        )
                    }
                }
                .onLogFailure {
                    _sideEffect.emit(ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    private fun getRegionList() {
        viewModelScope.launch {
            regionRepository.getRegionList()
                .onSuccess { response ->
                    val regionList = response.map { it.toExploreFilter() }.toImmutableList()
                    _state.update {
                        it.copy(
                            exploreFilterItems = it.exploreFilterItems.copy(
                                regions = regionList
                            )
                        )
                    }
                }
                .onLogFailure {
                    _sideEffect.emit(ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    fun localReviewToggle() {
        allSearchJob?.cancel()
        val chipItems = _state.value.chipItems
        val currentFilterState = _state.value.filterSelectionState
        val propertySelectedState = currentFilterState.properties
        val categorySelectedState = currentFilterState.categories
        val regionSelectedState = currentFilterState.regions
        val ageSelectedState = currentFilterState.ages
        val isLocalReviewSelected = !(propertySelectedState[2] ?: false)

        val updatedFilterOptions = chipItems.map { option ->
            when (option.sort) {
                FilterType.LOCAL_REVIEW -> {
                    option.copy(isSelected = isLocalReviewSelected)
                }

                FilterType.FILTER -> {
                    val isSelected = isLocalReviewSelected ||
                        categorySelectedState.any { (_, selected) -> selected } ||
                        regionSelectedState.any { (_, selected) -> selected } ||
                        ageSelectedState.any { (_, selected) -> selected }
                    option.copy(isSelected = isSelected)
                }

                else -> option
            }
        }.toImmutableList()
        _state.update {
            it.copy(
                filterSelectionState = currentFilterState.copy(
                    properties = propertySelectedState.put(2, isLocalReviewSelected)
                ),
                chipItems = updatedFilterOptions
            )
        }
        currentCursor = null
        getPlaceReviewListFiltered()
    }

    fun applyExploreFilter(propertySelectedState: PersistentMap<Int, Boolean>, categorySelectedState: PersistentMap<Int, Boolean>, regionSelectedState: PersistentMap<Int, Boolean>, ageSelectedState: PersistentMap<Int, Boolean>) {
        allSearchJob?.cancel()
        val chipItems = _state.value.chipItems
        val currentFilterState = _state.value.filterSelectionState
        val currentFilterItems = _state.value.exploreFilterItems
        val categoryItems = currentFilterItems.categories
        val regionItems = currentFilterItems.regions
        val ageItems = currentFilterItems.ages
        val updatedFilterOptions = chipItems.map { option ->
            when (option.sort) {
                FilterType.LOCAL_REVIEW -> {
                    val isSelected = propertySelectedState[2] ?: false
                    option.copy(isSelected = isSelected)
                }

                FilterType.CATEGORY -> {
                    val selectedCategories = categorySelectedState.filter { it.value }
                    val isSelected = selectedCategories.isNotEmpty()
                    val updatedText = when {
                        !isSelected -> option.sort.defaultText
                        selectedCategories.size == 1 -> {
                            categoryItems.find { it.id == selectedCategories.keys.first() }?.name ?: option.sort.defaultText
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
                        !isSelected -> option.sort.defaultText
                        selectedRegions.size == 1 -> {
                            regionItems.find { it.id == selectedRegions.keys.first() }?.name ?: option.sort.defaultText
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
                        !isSelected -> option.sort.defaultText
                        selectedAges.size == 1 -> {
                            ageItems.find { it.id == selectedAges.keys.first() }?.name ?: option.sort.defaultText
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
        currentCursor = null
        getPlaceReviewListFiltered()
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

    fun updateSelectedSortingOption(sortingOption: SortingOption) {
        allSearchJob?.cancel()
        _state.update {
            it.copy(
                selectedSortingOption = sortingOption,
                placeReviewList = UiState.Loading
            )
        }
        currentCursor = null
        getPlaceReviewListFiltered()
    }

    fun getPlaceReviewListFiltered() {
        val currentFilterState = _state.value.filterSelectionState
        val selectedCategoryIds = (currentFilterState.properties + currentFilterState.categories)
            .filterValues { it }
            .keys
            .toList()

        val selectedRegionIds = currentFilterState.regions
            .filterValues { it }
            .keys
            .toList()

        val selectedAgeGroups = currentFilterState.ages
            .filterValues { it }
            .keys
            .map {
                when (it) {
                    10 -> "AGE_10S"
                    20 -> "AGE_20S"
                    30 -> "AGE_30S"
                    else -> "AGE_ETC"
                }
            }
        allSearchJob = viewModelScope.launch {
            exploreRepository.getPlaceReviewListFiltered(
                categoryIds = selectedCategoryIds,
                regionIds = selectedRegionIds,
                ageGroups = selectedAgeGroups,
                sortBy = _state.value.selectedSortingOption.stringCode,
                cursor = currentCursor,
                size = EXPLORE_SEARCH_FETCH_SIZE
            )
                .onSuccess { (reviews, nextCursor) ->
                    _state.update {
                        val placeReviewList = (it.placeReviewList as? UiState.Success)?.data ?: persistentListOf()
                        if (currentCursor != null && nextCursor == null) return@launch
                        val newItems = reviews.map { placeReview -> placeReview.toModel() }.toPersistentList()
                        val mergedList =
                            if (currentCursor == null) {
                                newItems
                            } else {
                                (placeReviewList + newItems).toPersistentList()
                            }
                        currentCursor = nextCursor
                        it.copy(
                            placeReviewList = if (mergedList.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(mergedList)
                            }
                        )
                    }
                }
                .onLogFailure {
                    _state.update {
                        it.copy(
                            placeReviewList = UiState.Failure("버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                        )
                    }
                    _sideEffect.emit(ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    private fun getPlaceReviewFollowingList() {
        followingSearchJob = viewModelScope.launch {
            exploreRepository.getPlaceReviewListFollowing()
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
                                    }.toPersistentList()
                                )
                            }
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e)
                    _state.update {
                        it.copy(
                            placeReviewList = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                        )
                    }
                    _sideEffect.emit(ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                }
        }
    }

    fun refreshExploreScreen() {
        currentCursor = null
        if (state.value.exploreType == ExploreType.ALL) {
            getPlaceReviewListFiltered()
        } else {
            getPlaceReviewFollowingList()
        }
    }
    fun updateExploreType(exploreType: ExploreType) {
        if (state.value.exploreType == exploreType) return
        _state.update {
            it.copy(
                exploreType = exploreType,
                placeReviewList = UiState.Loading,
                selectedSortingOption = SortingOption.LATEST
            )
        }
        currentCursor = null
        when (exploreType) {
            ExploreType.ALL -> {
                followingSearchJob?.cancel()
                getPlaceReviewListFiltered()
            }
            ExploreType.FOLLOWING -> {
                allSearchJob?.cancel()
                getPlaceReviewFollowingList()
            }
        }
    }

    fun deleteReview(reviewId: Int) {
        viewModelScope.launch {
            postRepository.deletePost(reviewId)
                .onSuccess {
                    _state.update { currentState ->
                        val currentList = (currentState.placeReviewList as? UiState.Success)?.data ?: persistentListOf()
                        val updatedList = currentList.filterNot { it.reviewId == reviewId }.toPersistentList()

                        currentState.copy(
                            placeReviewList = if (updatedList.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(updatedList)
                            }
                        )
                    }
                    _sideEffect.emit(
                        ExploreSideEffect.ShowSnackbar("삭제 되었어요!")
                    )
                }
                .onFailure { e ->
                    Timber.e(e)
                    _sideEffect.emit(
                        ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                    )
                }
        }
    }
    companion object {
        private const val EXPLORE_SEARCH_FETCH_SIZE = 5
    }
}
