package com.spoony.spoony.presentation.explore

import androidx.lifecycle.ViewModel
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.CategoryEntity
import com.spoony.spoony.presentation.explore.model.FeedModel
import com.spoony.spoony.presentation.explore.type.SortingOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ExploreViewModel @Inject constructor() : ViewModel() {
    private var _state: MutableStateFlow<ExploreState> = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState>
        get() = _state

    init {
        _state.update {
            it.copy(
                feedList = UiState.Success(
                    persistentListOf(
                        FeedModel(
                            feedId = 0,
                            userId = 1,
                            username = "효비",
                            userRegion = "마포구",
                            title = "안녕!!",
                            categoryEntity = CategoryEntity(
                                categoryId = 1,
                                categoryName = "주류",
                                iconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                unSelectedIconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                textColor = "000000",
                                backgroundColor = "000000"
                            ),
                            addMapCount = 99
                        ),
                        FeedModel(
                            feedId = 1,
                            userId = 1,
                            username = "효비",
                            userRegion = "마포구",
                            title = "안녕!!",
                            categoryEntity = CategoryEntity(
                                categoryId = 1,
                                categoryName = "주류",
                                iconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                unSelectedIconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                textColor = "000000",
                                backgroundColor = "000000"
                            ),
                            addMapCount = 99
                        ),
                        FeedModel(
                            feedId = 2,
                            userId = 1,
                            username = "효비",
                            userRegion = "마포구",
                            title = "안녕!!",
                            categoryEntity = CategoryEntity(
                                categoryId = 1,
                                categoryName = "주류",
                                iconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                unSelectedIconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                textColor = "000000",
                                backgroundColor = "000000"
                            ),
                            addMapCount = 99
                        ),
                        FeedModel(
                            feedId = 3,
                            userId = 1,
                            username = "효비",
                            userRegion = "마포구",
                            title = "안녕!!",
                            categoryEntity = CategoryEntity(
                                categoryId = 1,
                                categoryName = "주류",
                                iconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                unSelectedIconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                textColor = "000000",
                                backgroundColor = "000000"
                            ),
                            addMapCount = 99
                        ),
                        FeedModel(
                            feedId = 4,
                            userId = 1,
                            username = "효비",
                            userRegion = "마포구",
                            title = "안녕!!",
                            categoryEntity = CategoryEntity(
                                categoryId = 1,
                                categoryName = "주류",
                                iconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                unSelectedIconUrl = "https://avatars.githubusercontent.com/u/93641814?s=60&v=4",
                                textColor = "000000",
                                backgroundColor = "000000"
                            ),
                            addMapCount = 99
                        )
                    )
                )
            )
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
