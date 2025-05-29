package com.spoony.spoony.presentation.exploreSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.database.entity.ExploreRecentSearchType
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.presentation.exploreSearch.model.toModel
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
    private val exploreRepository: ExploreRepository
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreSearchState> = MutableStateFlow(ExploreSearchState())
    val state: StateFlow<ExploreSearchState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ExploreSearchSideEffect>()
    val sideEffect: SharedFlow<ExploreSearchSideEffect>
        get() = _sideEffect.asSharedFlow()

    private var searchJob: Job? = null

    init {
        getFetchRecentSearchQueries()
    }

    private fun getFetchRecentSearchQueries() {
        viewModelScope.launch {
            listOf(
                ExploreRecentSearchType.REVIEW to { list: List<String> ->
                    _state.update {
                        it.copy(recentReviewSearchQueryList = list.toPersistentList())
                    }
                },
                ExploreRecentSearchType.USER to { list: List<String> ->
                    _state.update {
                        it.copy(recentUserSearchQueryList = list.toPersistentList())
                    }
                }
            ).forEach { (type, updateAction) ->
                exploreRepository.getExploreRecentSearches(type)
                    .onSuccess { response -> updateAction(response) }
                    .onLogFailure {
                        _sideEffect.emit(
                            ExploreSearchSideEffect.ShowSnackBar("최근 검색어를 불러오지 못했습니다.")
                        )
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
                    exploreRepository.insertExploreRecentSearch(type = ExploreRecentSearchType.USER, searchText = keywordTrim)
                    getFetchRecentSearchQueries()
                    exploreRepository.getUserListByKeyword(keywordTrim)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keywordTrim,
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
                    exploreRepository.insertExploreRecentSearch(type = ExploreRecentSearchType.REVIEW, searchText = keywordTrim)
                    getFetchRecentSearchQueries()
                    exploreRepository.getPlaceReviewByKeyword(keywordTrim)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keywordTrim,
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
                    exploreRepository.deleteExploreRecentSearch(type = ExploreRecentSearchType.USER, searchText = keyword)
                    getFetchRecentSearchQueries()
                }

                SearchType.REVIEW -> {
                    exploreRepository.deleteExploreRecentSearch(type = ExploreRecentSearchType.REVIEW, searchText = keyword)
                    getFetchRecentSearchQueries()
                }
            }
        }
    }

    fun clearRecentSearchItem() {
        viewModelScope.launch {
            when (_state.value.searchType) {
                SearchType.USER -> {
                    exploreRepository.clearExploreRecentSearch(type = ExploreRecentSearchType.USER)
                    getFetchRecentSearchQueries()
                }
                SearchType.REVIEW -> {
                    exploreRepository.clearExploreRecentSearch(type = ExploreRecentSearchType.REVIEW)
                    getFetchRecentSearchQueries()
                }
            }
        }
    }
}
