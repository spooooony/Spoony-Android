package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.repository.AuthRepository
import com.spoony.spoony.domain.repository.CategoryRepository
import com.spoony.spoony.domain.repository.ExploreRepository
import com.spoony.spoony.presentation.explore.model.toModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val authRepository: AuthRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private var _state: MutableStateFlow<ExploreState> = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState>
        get() = _state

    private val _sideEffect = Channel<ExploreSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        getAllFeedList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            categoryRepository.getCategories()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            categoryList = UiState.Success(response.toImmutableList())
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            categoryList = UiState.Failure("카테고리 목록 조회 실패")
                        )
                    }
                }
        }
    }

    fun getSpoonAccount() {
        viewModelScope.launch {
            authRepository.getSpoonCount()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            spoonCount = UiState.Success(response)
                        )
                    }
                }
        }
    }

    fun getFeedList() {
        viewModelScope.launch {
            exploreRepository.getFeedList(
                categoryId = _state.value.selectedCategoryId,
                locationQuery = _state.value.selectedCity,
                sortBy = _state.value.selectedSortingOption.stringCode
            )
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            feedList =
                            if (response.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.Success(
                                    response.map { feed ->
                                        feed.toModel()
                                    }.toImmutableList()
                                )
                            }
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            feedList = UiState.Failure("피드 목록 조회 실패")
                        )
                    }
                }
        }
    }

    private fun getAllFeedList() {
        viewModelScope.launch {
            try {
                exploreRepository.getAllFeedList()
                    .onSuccess { response ->
                        _state.update {
                            it.copy(
                                placeReviewList =
                                if (response.isEmpty()) {
                                    UiState.Empty
                                } else {
                                    UiState.Success(
                                        response.map { placeReview ->
                                            placeReview.toModel()
                                        }.toImmutableList()
                                    )
                                }
                            )
                        }
                    }
                    .onFailure {
                        _state.update {
                            it.copy(
                                placeReviewList = UiState.Failure("피드 목록 조회 실패")
                            )
                        }
                        _sideEffect.send(ExploreSideEffect.ShowSnackbar("서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요."))
                    }
            } catch (e: Exception) {
                _sideEffect.send(ExploreSideEffect.ShowSnackbar("예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."))
            }
        }
    }

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

sealed class ExploreSideEffect {
    data class ShowSnackbar(val message: String) : ExploreSideEffect()
}
