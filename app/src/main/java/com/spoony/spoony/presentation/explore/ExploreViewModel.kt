package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.ExploreRepository
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
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            runCatching {
                exploreRepository.getCategoryList()
                    .onSuccess { response ->
                        _state.update {
                            it.copy(
                                categoryList = UiState.Success(response.toImmutableList())
                            )
                        }
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
