package com.spoony.spoony.presentation.exploreSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.data.datasource.local.RecentSearchDataSource
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.presentation.exploreSearch.model.toModel
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
class ExploreSearchViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val recentSearchDataSource: RecentSearchDataSource
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreSearchState> = MutableStateFlow(ExploreSearchState())
    val state: StateFlow<ExploreSearchState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ExploreSearchSideEffect>()
    val sideEffect: SharedFlow<ExploreSearchSideEffect>
        get() = _sideEffect.asSharedFlow()

    private var searchJob: Job? = null

    init {
        observeRecentSearchQueries()
    }

    private fun observeRecentSearchQueries() {
        viewModelScope.launch {
            launch {
                recentSearchDataSource.getRecentReviewQueries()
                    .collect { reviewQueries ->
                        _state.update {
                            it.copy(recentReviewSearchQueryList = reviewQueries.toPersistentList())
                        }
                    }
            }

            launch {
                recentSearchDataSource.getRecentUserQueries()
                    .collect { userQueries ->
                        _state.update {
                            it.copy(recentUserSearchQueryList = userQueries.toPersistentList())
                        }
                    }
            }
        }
    }

    fun switchSearchType(
        searchType: SearchType
    ) {
        searchJob?.cancel()
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
        searchJob?.cancel()
        _state.update {
            it.copy(
                searchKeyword = ""
            )
        }
    }
    private fun makeSearchHistoryList(
        filteredKeyword: String,
        rawList: List<String>,
        newKeywordList: List<String> = listOf(),
        maxSize: Int = 6
    ): ImmutableList<String> {
        return (newKeywordList + rawList.filterNot { it == filteredKeyword })
            .take(maxSize)
            .toPersistentList()
    }

    fun search(keyword: String) {
        val keywordTrim = keyword.trim()
        if (keywordTrim.isBlank()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            when (_state.value.searchType) {
                SearchType.USER -> {
                    _state.update {
                        it.copy(
                            userInfoList = UiState.Loading
                        )
                    }
                    val updatedList = makeSearchHistoryList(keywordTrim, _state.value.recentUserSearchQueryList, listOf(keywordTrim))
                    recentSearchDataSource.setUserQueries(updatedList)
                    exploreRepository.getUserListByKeyword(keywordTrim)
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
                        .onFailure { exception ->
                            Timber.e(exception)
                            _state.update {
                                it.copy(
                                    userInfoList = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                                )
                            }
                            _sideEffect.emit(
                                ExploreSearchSideEffect.ShowSnackBar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                            )
                        }
                }

                SearchType.REVIEW -> {
                    _state.update {
                        it.copy(
                            placeReviewInfoList = UiState.Loading
                        )
                    }
                    val updatedList = makeSearchHistoryList(keywordTrim, _state.value.recentReviewSearchQueryList, listOf(keywordTrim))
                    recentSearchDataSource.setReviewQueries(updatedList)
                    exploreRepository.getPlaceReviewByKeyword(keywordTrim)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keywordTrim,
                                    recentReviewSearchQueryList = updatedList,
                                    placeReviewInfoList = if (response.isEmpty()) {
                                        UiState.Empty
                                    } else {
                                        UiState.Success(
                                            response.map { placeReviewEntity ->
                                                placeReviewEntity.toModel()
                                            }.toPersistentList()
                                        )
                                    }
                                )
                            }
                        }
                        .onFailure { exception ->
                            Timber.e(exception)
                            _state.update {
                                it.copy(
                                    placeReviewInfoList = UiState.Failure("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                                )
                            }
                            _sideEffect.emit(
                                ExploreSearchSideEffect.ShowSnackBar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.")
                            )
                        }
                }
            }
        }
    }

    fun removeRecentSearchItem(keyword: String) {
        viewModelScope.launch {
            when (_state.value.searchType) {
                SearchType.USER -> {
                    val updatedList = makeSearchHistoryList(keyword, _state.value.recentUserSearchQueryList)
                    recentSearchDataSource.setUserQueries(updatedList)
                    _state.update {
                        it.copy(
                            recentUserSearchQueryList = updatedList
                        )
                    }
                }

                SearchType.REVIEW -> {
                    val updatedList = makeSearchHistoryList(keyword, _state.value.recentReviewSearchQueryList)
                    recentSearchDataSource.setReviewQueries(updatedList)
                    _state.update {
                        it.copy(
                            recentReviewSearchQueryList = updatedList
                        )
                    }
                }
            }
        }
    }

    fun clearRecentSearchItem() {
        viewModelScope.launch {
            when (_state.value.searchType) {
                SearchType.USER -> {
                    recentSearchDataSource.clearUserQueries()
                    _state.update {
                        it.copy(
                            recentUserSearchQueryList = persistentListOf()
                        )
                    }
                }
                SearchType.REVIEW -> {
                    recentSearchDataSource.clearReviewQueries()
                    _state.update {
                        it.copy(
                            recentReviewSearchQueryList = persistentListOf()
                        )
                    }
                }
            }
        }
    }
}
