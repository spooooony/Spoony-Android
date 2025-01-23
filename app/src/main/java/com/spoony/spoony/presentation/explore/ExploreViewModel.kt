package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.presentation.explore.model.toModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreState> = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState>
        get() = _state

    init {
        getCategoryList()
        getFeedList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            exploreRepository.getCategoryList()
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

    fun getFeedList() {
        viewModelScope.launch {
            exploreRepository.getFeedList(
                userId = 30,
                categoryId = _state.value.selectedCategoryId,
                locationQuery = _state.value.selectedCity,
                sortBy = _state.value.selectedSortingOption.stringCode
            )
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            feedList =
                            if (response.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(
                                    response.map { feed ->
                                        feed.toModel()
                                    }.toImmutableList()
                                )
                            }
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            feedList = UiState.Failure("피드 목록 조회 실패")
                        )
                    }
                }
        }
    }

    fun updateSelectedSortingOption(sortingOption: SortingOption) {
        _state.update {
            it.copy(
                selectedSortingOption = sortingOption
            )
        }
    }

    fun updateSelectedCity(city: String) {
        _state.update {
            it.copy(
                selectedCity = city
            )
        }
    }

    fun updateSelectedCategory(categoryId: Int) {
        _state.update {
            it.copy(
                selectedCategoryId = categoryId
            )
        }
    }
}
