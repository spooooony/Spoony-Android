package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ExploreViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<ExploreState> = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState>
        get() = _state

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
