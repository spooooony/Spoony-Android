package com.spoony.spoony.presentation.map.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.MapRepository
import com.spoony.spoony.presentation.map.search.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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

    init {
        _state.update {
            it.copy(
                recentSearchQueryList = persistentListOf(
                    "디쟌쌤 안녕하세요",
                    "QA 잘 부탁드립니다",
                    "디자인 너무 예뻐요",
                    "사랑합니다",
                    "😘😘😘😘"
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

    fun searchLocation() {
        viewModelScope.launch {
            mapRepository.searchLocation(_state.value.searchKeyword)
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
                }
                .onFailure {
                    Log.d("TAG", "searchLocation: $it")
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
}
