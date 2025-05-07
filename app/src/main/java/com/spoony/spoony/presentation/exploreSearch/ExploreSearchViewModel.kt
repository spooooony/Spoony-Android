package com.spoony.spoony.presentation.exploreSearch

import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ExploreSearchViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<ExploreSearchState> = MutableStateFlow(ExploreSearchState())
    val state: StateFlow<ExploreSearchState>
        get() = _state.asStateFlow()

    fun switchType(
        searchType: SearchType
    ) {
        _state.update {
            it.copy(
                searchType = searchType,
                searchKeyword = ""
            )
        }
    }

    fun clearSearchKeyword() {
        _state.update {
            it.copy(
                searchKeyword = ""
            )
        }
    }

    fun search(keyword: String) {
        val keywordTrim = keyword.trim()
        when (_state.value.searchType) {
            SearchType.USER -> {
                val updatedList = (listOf(keywordTrim) + _state.value.recentUserSearchQueryList.filterNot { it == keyword })
                    .take(6)
                    .toPersistentList()
                _state.update {
                    it.copy(
                        searchKeyword = keywordTrim,
                        recentUserSearchQueryList = updatedList,
                        userInfoList = UiState.Success(persistentListOf())
                    )
                }
            }
            SearchType.REVIEW -> {
                val updatedList = (listOf(keywordTrim) + _state.value.recentReviewSearchQueryList.filterNot { it == keyword })
                    .take(6)
                    .toPersistentList()
                _state.update {
                    it.copy(
                        searchKeyword = keywordTrim,
                        recentUserSearchQueryList = updatedList
                    )
                }
            }
        }
    }

    fun removeRecentSearchItem(keyword: String) {
        when (_state.value.searchType) {
            SearchType.USER -> {
                val updatedList = _state.value.recentUserSearchQueryList.filterNot { it == keyword }.toPersistentList()
                _state.update {
                    it.copy(
                        recentUserSearchQueryList = updatedList
                    )
                }
            }
            SearchType.REVIEW -> {
                val updatedList = _state.value.recentReviewSearchQueryList.filterNot { it == keyword }.toPersistentList()
                _state.update {
                    it.copy(
                        recentReviewSearchQueryList = updatedList
                    )
                }
            }
        }
    }

    fun clearRecentSearchItem() {
        when (_state.value.searchType) {
            SearchType.USER -> {
                _state.update {
                    it.copy(
                        recentUserSearchQueryList = persistentListOf()
                    )
                }
            }
            SearchType.REVIEW -> {
                _state.update {
                    it.copy(
                        recentReviewSearchQueryList = persistentListOf()
                    )
                }
            }
        }
    }
}
