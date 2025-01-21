package com.spoony.spoony.presentation.map.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MapSearchViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<MapSearchState> = MutableStateFlow(MapSearchState())
    val state: StateFlow<MapSearchState>
        get() = _state.asStateFlow()

    fun updateSearchKeyword(searchKeyword: String) {
        _state.update {
            it.copy(
                searchKeyword = searchKeyword
            )
        }
    }
}
