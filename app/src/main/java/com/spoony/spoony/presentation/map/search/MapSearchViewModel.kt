package com.spoony.spoony.presentation.map.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MapSearchViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<MapSearchState> = MutableStateFlow(MapSearchState())
    val state: StateFlow<MapSearchState>
        get() = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                recentSearchQueryList = persistentListOf(
                    "디쟌쌤 안녕하세요",
                    "QA 잘 부탁드립니다",
                    "디자인 너무 예뻐요",
                    "사랑합니다",
                    "😘😘😘😘",
                )
            )
        }
    }

    fun initRecentSearch() {
        _state.update {
            it.copy(
                recentSearchQueryList = persistentListOf()
            )
        }
    }

    fun updateSearchKeyword(searchKeyword: String) {
        _state.update {
            it.copy(
                searchKeyword = searchKeyword
            )
        }
    }
}
