package com.spoony.spoony.presentation.map.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.presentation.map.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {
    private var _state: MutableStateFlow<MapSearchState> = MutableStateFlow(MapSearchState())
    val state: StateFlow<MapSearchState>
        get() = _state.asStateFlow()

    fun initRecentSearch() {
        _state.update {
            it.copy(
                recentSearchQueryList = persistentListOf()
            )
        }
    }

    fun searchLocation() {
        viewModelScope.launch {
            val searchText = _state.value.searchKeyword.replace(" ", "")

            mapRepository.searchLocation(searchText)
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            locationModelList = if (response.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(
                                    response.map { location ->
                                        location.toModel()
                                    }.toImmutableList()
                                )
                            }
                        )
                    }

                    mapRepository.addSearch(searchText)
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            locationModelList = UiState.Failure("지역 목록 검색 실패")
                        )
                    }
                }
        }
    }

    fun updateSearchKeyword(searchKeyword: String) {
        _state.update {
            it.copy(
                searchKeyword = searchKeyword
            )
        }
    }

    fun initLocationModelList() {
        _state.update {
            it.copy(
                locationModelList = UiState.Loading
            )
        }
    }

    fun getAllSearches() {
        viewModelScope.launch {
            mapRepository.getRecentSearches().onSuccess { data ->
                _state.update {
                    it.copy(
                        recentSearchQueryList = data.toPersistentList()
                    )
                }
            }
        }
    }

    fun deleteAllSearches() {
        viewModelScope.launch {
            mapRepository.deleteAllSearches().onSuccess {
                _state.update {
                    it.copy(
                        recentSearchQueryList = persistentListOf()
                    )
                }
            }
        }
    }

    fun deleteSearchByText(searchText: String) {
        viewModelScope.launch {
            mapRepository.deleteSearchByText(searchText).onSuccess {
                _state.update {
                    it.copy(
                        recentSearchQueryList = it.recentSearchQueryList.filter { text ->
                            text != searchText
                        }.toPersistentList()
                    )
                }
            }
        }
    }
}
