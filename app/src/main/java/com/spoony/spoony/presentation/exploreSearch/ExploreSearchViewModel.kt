package com.spoony.spoony.presentation.exploreSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.database.entity.ExploreRecentSearchType
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.domain.repository.PostRepository
import com.spoony.spoony.presentation.exploreSearch.model.toModel
import com.spoony.spoony.presentation.exploreSearch.type.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

@HiltViewModel
class ExploreSearchViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val postRepository: PostRepository
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
                    .onLogFailure {}
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
                    exploreRepository.insertExploreRecentSearch(type = ExploreRecentSearchType.USER, searchText = keyword)
                    getFetchRecentSearchQueries()
                    exploreRepository.getUserListByKeyword(keyword)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keyword,
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
                        .onLogFailure {
                            _state.update {
                                it.copy(
                                    userInfoList = UiState.Failure(ErrorType.SERVER_CONNECTION_ERROR.description)
                                )
                            }
                            _sideEffect.emit(
                                ExploreSearchSideEffect.ShowSnackBar(ErrorType.SERVER_CONNECTION_ERROR.description)
                            )
                        }
                }

                SearchType.REVIEW -> {
                    _state.update {
                        it.copy(
                            placeReviewInfoList = UiState.Loading
                        )
                    }
                    exploreRepository.insertExploreRecentSearch(type = ExploreRecentSearchType.REVIEW, searchText = keyword)
                    getFetchRecentSearchQueries()
                    exploreRepository.getPlaceReviewByKeyword(keyword)
                        .onSuccess { response ->
                            _state.update {
                                it.copy(
                                    searchKeyword = keyword,
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
                        .onLogFailure {
                            _state.update {
                                it.copy(
                                    placeReviewInfoList = UiState.Failure(ErrorType.SERVER_CONNECTION_ERROR.description)
                                )
                            }
                            _sideEffect.emit(
                                ExploreSearchSideEffect.ShowSnackBar(ErrorType.SERVER_CONNECTION_ERROR.description)
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

    fun deleteReview(reviewId: Int) {
        viewModelScope.launch {
            postRepository.deletePost(reviewId)
                .onSuccess {
                    _state.update { currentState ->
                        val currentList = (currentState.placeReviewInfoList as? UiState.Success)?.data ?: persistentListOf()
                        val updatedList = currentList.filterNot { it.reviewId == reviewId }.toPersistentList()

                        currentState.copy(
                            placeReviewInfoList = if (updatedList.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(updatedList)
                            }
                        )
                    }
                    _sideEffect.emit(
                        ExploreSearchSideEffect.ShowSnackBar("삭제 되었어요!")
                    )
                }
                .onLogFailure {
                    _sideEffect.emit(
                        ExploreSearchSideEffect.ShowSnackBar(ErrorType.SERVER_CONNECTION_ERROR.description)
                    )
                }
        }
    }

    fun refresh() {
        search(_state.value.searchKeyword)
    }
}
