package com.spoony.spoony.presentation.exploreSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.presentation.exploreSearch.model.toModel
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ExploreSearchViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreSearchState> = MutableStateFlow(ExploreSearchState())
    val state: StateFlow<ExploreSearchState>
        get() = _state.asStateFlow()

    fun switchSearchType(
        searchType: SearchType
    ) {
        _state.update {
            it.copy(
                searchType = searchType,
                userInfoList = UiState.Loading,
                placeReviewInfoList = UiState.Loading
            )
        }
        search(_state.value.searchKeyword)
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
        if (keyword.isBlank()) return
        viewModelScope.launch {
            when (_state.value.searchType) {
                SearchType.USER -> {
                    val updatedList = (listOf(keywordTrim) + _state.value.recentUserSearchQueryList.filterNot { it == keyword })
                        .take(6)
                        .toPersistentList()

                    exploreRepository.getUserListSearchByKeyword(keywordTrim)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keywordTrim,
                                    recentUserSearchQueryList = updatedList,
                                    userInfoList = if (response.isEmpty()) {
                                        UiState.Empty
                                    } else {
                                        UiState.Success(
                                            response.map { userEntity ->
                                                userEntity.toModel()
                                            }.toPersistentList()
                                        )
                                    }
                                )
                            }
                        }
                        .onFailure(Timber::e)
                }

                SearchType.REVIEW -> {
                    val updatedList = (listOf(keywordTrim) + _state.value.recentReviewSearchQueryList.filterNot { it == keyword })
                        .take(6)
                        .toPersistentList()
                    exploreRepository.getPlaceReviewSearchByKeyword(keywordTrim)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keywordTrim,
                                    recentReviewSearchQueryList = updatedList,
                                    placeReviewInfoList = if (response.isEmpty()) {
                                        UiState.Empty
                                    } else {
                                        UiState.Success(
                                            response.map { userEntity ->
                                                userEntity.toModel()
                                            }.toPersistentList()
                                        )
                                    }
                                )
                            }
                        }
                        .onFailure(Timber::e)
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
